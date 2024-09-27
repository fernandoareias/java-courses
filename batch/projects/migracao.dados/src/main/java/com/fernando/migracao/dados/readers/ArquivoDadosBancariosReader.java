package com.fernando.migracao.dados.readers;

import com.fernando.migracao.dados.domain.DadosBancarios;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ArquivoDadosBancariosReader {

    @Bean
    public FlatFileItemReader<DadosBancarios> dadosBancariosFlatFileItemReader()
    {
        return new FlatFileItemReaderBuilder<DadosBancarios>()
                .name("dadosBancariosFlatFileItemReader")
                .resource(new FileSystemResource("/Users/f.areias/Desktop/java-courses/batch/projects/migracao.dados/src/main/resources/files/dados_bancarios.csv"))
                .delimited()
                .names("pessoa_id", "agencia", "conta", "banco", "id")
                .addComment("--")
                .targetType(DadosBancarios.class)
                .build();
    }

}
