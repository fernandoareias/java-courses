package com.fernando.migracao.dados.steps;

import com.fernando.migracao.dados.domain.DadosBancarios;
import com.fernando.migracao.dados.domain.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrarDadosBancariosStep {

    @Autowired
    private StepBuilder stepBuilder;

    @Bean
    public Step migrarDadosBancariosStep(
            ItemReader<DadosBancarios> arquivoDadosBancariosReader,
            ItemWriter<DadosBancarios> bancoDadosBancariosWriter
    ){
        return new StepBuilder("migrarDadosBancarios")
                .<DadosBancarios, DadosBancarios>chunk(1)
                .reader(arquivoDadosBancariosReader)
                .writer(bancoDadosBancariosWriter)
                .build();
    }
}
