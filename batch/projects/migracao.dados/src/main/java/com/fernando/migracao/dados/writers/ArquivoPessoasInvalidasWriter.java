package com.fernando.migracao.dados.writers;

import com.fernando.migracao.dados.domain.Pessoa;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ArquivoPessoasInvalidasWriter {

    @Bean
    public FlatFileItemWriter<Pessoa> pessoaInvalidaFlatItemWriter()
    {
        return new FlatFileItemWriterBuilder<Pessoa>()
                .name("pessoaInvalidaFlatItemWriter")
                .resource(new FileSystemResource("/Users/f.areias/Desktop/java-courses/batch/projects/migracao.dados/src/main/resources/files/pessoa_invalida.txt"))
                .delimited()
                .names("id")
                .build();
    }
}
