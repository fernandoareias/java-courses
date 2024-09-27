package com.fernando.migracao.dados.readers;

import com.fernando.migracao.dados.domain.Pessoa;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import java.util.Date;

@Configuration
public class ArquivoPessoaReader {

    @Bean
    public FlatFileItemReader<Pessoa> pessoaItemReader()
    {
        return new FlatFileItemReaderBuilder<Pessoa>()
                .name("pessoaItemReader")
                .resource(new FileSystemResource("/Users/f.areias/Desktop/java-courses/batch/projects/migracao.dados/src/main/resources/files/pessoas.csv"))
                .delimited()
                .names("nome", "email", "dataNascimento", "idade", "id")
                .addComment("--")
                .fieldSetMapper(fieldSetMapper())
                .build();
    }

    private FieldSetMapper<Pessoa> fieldSetMapper() {
        return new FieldSetMapper<Pessoa>() {
            @Override
            public Pessoa mapFieldSet(FieldSet fieldSet) throws BindException {
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(fieldSet.readString("nome"));
                pessoa.setEmail(fieldSet.readString("email"));
                pessoa.setDataNascimento(new Date(fieldSet.readDate("dataNascimento", "yyyy-MM-dd HH:mm:ss").getTime()));
                pessoa.setIdade(fieldSet.readInt("idade"));
                pessoa.setId(fieldSet.readLong("id"));

                return pessoa;
            }
        };
    }

}
