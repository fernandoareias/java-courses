package com.fernando.migracao.dados.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.batch.core.job.flow.Flow; // Import correto

@Configuration
@EnableBatchProcessing
public class MigracaoDadosJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job migracaoDadoJob(
            @Qualifier("migrarPessoa") Step migrarPessoaStep,
            @Qualifier("migrarDadosBancarios") Step migrarBancarioStep
    ) {
        return jobBuilderFactory.get("migracaoDadoJob")
                .start(startStepsParalelo(migrarPessoaStep, migrarBancarioStep)) // Inicia com steps em paralelo
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Flow startStepsParalelo(Step migrarPessoaStep, Step migrarBancarioStep) {
        Flow migrarDadosBancariosFlow = new FlowBuilder<Flow>("migrarDadosBancarioFlow")
                .start(migrarBancarioStep)
                .build();

        Flow stepsParalelos = new FlowBuilder<Flow>("stepsParalelosFlow")
                .start(migrarPessoaStep)
                .split(new SimpleAsyncTaskExecutor()) // Divisão para execução em paralelo
                .add(migrarDadosBancariosFlow)
                .build();

        return stepsParalelos;
    }
}
