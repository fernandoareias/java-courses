package com.fernando.migracao.dados.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class MigracaoDadosJob {

    @Autowired
    private JobBuilder jobBuilderFactory;

    @Bean
    public Job migracaoDadosJob(
         @Qualifier("migrarPessoa") Step migrarPessoaStep,
         @Qualifier("migrarDadosBancarios") Step migrarBancarioStep
    ){
        return jobBuilderFactory
                .start(migrarPessoaStep)
                .next(migrarBancarioStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
