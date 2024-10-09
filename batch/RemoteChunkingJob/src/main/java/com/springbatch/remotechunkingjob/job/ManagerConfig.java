package com.springbatch.remotechunkingjob.job;

import com.springbatch.remotechunkingjob.dominio.Pessoa;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.transaction.PlatformTransactionManager;

@Profile("manager")
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
public class ManagerConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private RemoteChunkingManagerStepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("transactionManagerApp")
    private PlatformTransactionManager transactionManager;


    @Bean
    public Job remoteChunckingJob(
            @Qualifier("migrarPessoaWorkerStep")Step migrarPessoaStep,
            @Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep
            ){
        return jobBuilderFactory
                .get("remoteChunckingJob")
                .start(migrarPessoaStep)
                .next(migrarDadosBancariosStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step migrarPessoaWorkerStep(
            ItemReader<Pessoa> arquivoPessoaReader
    ){
        return stepBuilderFactory
                .get("migrarPessoaWorkerStep")
                .chunk(15)
                .reader(arquivoPessoaReader)
                .outputChannel(requests())
                .inputChannel(replies())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    private QueueChannel replies() {
        return new QueueChannel();
    }

    @Bean
    private DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outBoundFlow(ActiveMQConnectionFactory connection)
    {
        return IntegrationFlows
                .from(requests())
                .handle(Jms.outboundAdapter(connection).destination("requests"))
                .get();
    }

    @Bean
    public IntegrationFlow inBoundedFlow(ActiveMQConnectionFactory connection)
    {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(connection)
                        .destination("replies"))
                .channel(replies())
                .get();
    }

}
