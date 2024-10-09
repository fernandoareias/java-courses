package com.springbatch.bdremotepartitioner.job;

import com.springbatch.bdremotepartitioner.dominio.DadosBancarios;
import com.springbatch.bdremotepartitioner.dominio.Pessoa;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningManagerStepBuilderFactory;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
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
    private RemotePartitioningWorkerStepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("transactionManagerApp")
    private PlatformTransactionManager transactionManager;


    @Bean
    public Step migrarPessoaStep( JdbcPagingItemReader<Pessoa> pessoaReader,
                                  JdbcBatchItemWriter<Pessoa> pessoaWriter) {
        return stepBuilderFactory
                .get("migrarPessoaStep")
                .inputChannel(requests())
                .<Pessoa, Pessoa>chunk(10000)
                .reader(pessoaReader)
                .writer(pessoaWriter)
                .transactionManager(transactionManager)
                .build();

    }

    private DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public Step migrarDadosBancarios(JdbcPagingItemReader<DadosBancarios> dadosBancariosReader,
                                     JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter) {
        return stepBuilderFactory
                .get("migrarDadosBancarios")
                .<DadosBancarios, DadosBancarios>chunk(10000)
                .reader(dadosBancariosReader)
                .writer(dadosBancariosWriter)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connection)
    {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(connection)
                        .destination("requests"))
                .channel(requests())
                .get();
    }
}
