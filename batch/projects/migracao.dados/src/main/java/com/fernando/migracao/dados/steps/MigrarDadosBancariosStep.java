package com.fernando.migracao.dados.steps;

import com.fernando.migracao.dados.domain.DadosBancarios;
import com.fernando.migracao.dados.domain.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MigrarDadosBancariosStep {


    @Bean
    public Step migrarDadosBancarios(
            ItemReader<DadosBancarios> arquivoDadosBancariosReader,
            JdbcBatchItemWriter<DadosBancarios> bancoDadosBancariosWriter,
            JobRepository jobRepository
    ){
        return new StepBuilder("migrarDadosBancarios", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(1)
                .reader(arquivoDadosBancariosReader)
                .writer(bancoDadosBancariosWriter)
                .build();
    }
}
