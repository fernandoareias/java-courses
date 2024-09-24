package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ArquivoMultiplosFormatosReaderConfig {

	@SuppressWarnings("unchecked")
	@StepScope
	@Bean
	public FlatFileItemReader arquivoMultiplosFormatosItemReader(@Value("classpath:files/clientes.txt")
																 Resource arquivoCliente,
																 LineMapper lineMapper) {
		return new FlatFileItemReaderBuilder()
				.name("arquivoMultiplosFormatosItemReader")
				.resource(arquivoCliente)
				.lineMapper(lineMapper)
				.build();
	}

}
