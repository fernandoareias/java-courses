package com.springbatch.remotechunkingjob.job;

import com.springbatch.remotechunkingjob.dominio.Pessoa;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.messaging.MessageChannel;
import org.springframework.transaction.PlatformTransactionManager;

@Profile("worker")
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
public class WorkerConfig {

    @Autowired
    private RemoteChunkingWorkerBuilder<Pessoa, Pessoa> pessoaWorkerBuilder;

//    @Autowired
//    @Qualifier("transactionManagerApp")
//    private PlatformTransactionManager transactionManager;


    @Bean
    public IntegrationFlow integrationFlow(
            ItemProcessor<Pessoa, Pessoa> itemProcessor,
            ItemWriter itemWriter
    ){
        return pessoaWorkerBuilder
                .itemProcessor(itemProcessor)
                .itemWriter(itemWriter)
                .inputChannel(requests())
                .outputChannel(replies())
                .build();
    }

    private DirectChannel replies() {
        return new DirectChannel();
    }

    @Bean
    private DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outBoundFlow(ActiveMQConnectionFactory connection)
    {
        return IntegrationFlows
                .from(replies())
                .handle(Jms.outboundAdapter(connection).destination("replies"))
                .get();
    }

    @Bean
    public IntegrationFlow inBoundedFlow(ActiveMQConnectionFactory connection)
    {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(connection)
                        .destination("requests"))
                .channel(requests())
                .get();
    }


}
