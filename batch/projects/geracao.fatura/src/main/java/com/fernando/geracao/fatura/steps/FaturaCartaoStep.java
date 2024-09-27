package com.fernando.geracao.fatura.steps;

import com.fernando.geracao.fatura.dominio.FaturaCartaoCredito;
import com.fernando.geracao.fatura.dominio.Transacao;
import com.fernando.geracao.fatura.readers.JdbcFaturaCartaoCreditoReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaturaCartaoStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step faturaCartaoStep(
            ItemStreamReader<Transacao> lerCartaoCredito,
            ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> processarFaturaCartaoCredito,
            ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCredito
    ){
        return stepBuilderFactory.get("faturaCartaoStep")
                .<FaturaCartaoCredito, FaturaCartaoCredito>chunk(1)
                .reader(new JdbcFaturaCartaoCreditoReader(lerCartaoCredito))
                .processor(processarFaturaCartaoCredito)
                .writer(escreverFaturaCartaoCredito)
                .build();
    }
}
