package com.fernando.migracao.dados.steps;

import com.fernando.migracao.dados.domain.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrarPessoaStep {

    @Autowired
    private StepBuilder stepBuilder;

    @Bean
    public Step migrarPessoaStep(
        ItemReader<Pessoa> arquivoPessoaReader,
        ItemWriter<Pessoa> bancoPessoaWriter
    ){
        return new StepBuilder("migrarPessoa")
                .<Pessoa, Pessoa>chunk(1)
                .reader(arquivoPessoaReader)
                .writer(bancoPessoaWriter)
                .build();
    }
}
