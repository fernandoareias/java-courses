package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class FileImpressaoContaWriterConfig {

    @Bean
    public FlatFileItemWriter<Conta> fileContaWriter(){
        return new FlatFileItemWriterBuilder<Conta>()
                .name("fileContaWriter")
                .resource(new FileSystemResource("/Users/f.areias/Desktop/java-courses/batch/writers/ContasBancariasJob/src/main/resources/files/contas.txt"))
                .delimited()
                .names("tipo", "limite", "clienteId")
                .build();
    }
}
