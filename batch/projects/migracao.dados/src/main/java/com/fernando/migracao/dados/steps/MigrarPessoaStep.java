package com.fernando.migracao.dados.steps;

import com.fernando.migracao.dados.domain.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrarPessoaStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step migrarPessoa(
        ItemReader<Pessoa> arquivoPessoaReader,
        ClassifierCompositeItemWriter<Pessoa> pessoaClassifierCompositeItemWriter,
        FlatFileItemWriter<Pessoa> arquivoPessoaInvalida
    ){
        return stepBuilderFactory.get("migrarPessoa")
                .<Pessoa, Pessoa>chunk(1)
                .reader(arquivoPessoaReader)
                .writer(pessoaClassifierCompositeItemWriter)
                .stream(arquivoPessoaInvalida)
                .build();
    }
}
