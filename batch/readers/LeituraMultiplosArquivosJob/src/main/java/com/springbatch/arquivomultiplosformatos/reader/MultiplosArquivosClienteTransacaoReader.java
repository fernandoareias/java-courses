package com.springbatch.arquivomultiplosformatos.reader;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultiplosArquivosClienteTransacaoReader {

    @Bean
    public MultiResourceItemReader multiResourceItemReader(
            @Value("classpath:files/clientes*.txt") Resource[] arquivosClientes,
            FlatFileItemReader reader)
    {
        return new MultiResourceItemReaderBuilder()
                .name("multiResourceItemReader")
                .resources(arquivosClientes)
                .delegate(new ArquivoClienteTransacaoReader(reader))
                .build();
    }
}
