package com.springbatch.simplepartitionerlocal.writer;

import com.springbatch.simplepartitionerlocal.dominio.DadosBancarios;
import com.springbatch.simplepartitionerlocal.dominio.Pessoa;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class MultiResourceItemWriterConfig {

    @Bean
    public MultiResourceItemWriter<Pessoa> pessoaMultiResourceItemWriter(){
        return new MultiResourceItemWriterBuilder<Pessoa>()
                .name("pessoaMultiResourceItemWriter")
                .delegate(pessoaWriter())
                .resource(new FileSystemResource("files/pessoa-tmp"))
                .itemCountLimitPerResource(2000)
                .build();
    }

    private FlatFileItemWriter<Pessoa> pessoaWriter() {
        return new FlatFileItemWriterBuilder<Pessoa>()
                .name("pessoaWriter")
                .delimited()
                .names("nome", "email", "dataNascimentoStr", "idade", "id")
                .build();
    }


    @Bean
    public MultiResourceItemWriter<DadosBancarios> dadosBancariosMultiResourceItemWriter(){
        return new MultiResourceItemWriterBuilder<DadosBancarios>()
                .name("dadosBancariosMultiResourceItemWriter")
                .delegate(dadosBancariosFlatFileItemWriter())
                .resource(new FileSystemResource("files/dados_bancarios-tmp"))
                .itemCountLimitPerResource(2000)
                .build();
    }

    private FlatFileItemWriter<DadosBancarios> dadosBancariosFlatFileItemWriter() {
        return new FlatFileItemWriterBuilder<DadosBancarios>()
                .name("dadosBancariosFlatFileItemWriter")
                .delimited()
                .names("pessoaId", "agencia", "conta", "banco", "id")
                .build();
    }

}
