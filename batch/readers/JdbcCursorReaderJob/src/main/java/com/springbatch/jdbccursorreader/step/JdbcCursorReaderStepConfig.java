package com.springbatch.jdbccursorreader.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.jdbccursorreader.dominio.Cliente;

import java.sql.SQLException;

@Configuration
public class JdbcCursorReaderStepConfig {
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step jdbcCursorReaderStep(ItemReader<Cliente> jdbcCursorReader, ItemWriter<Cliente> jdbcCursorWriter) {
		return stepBuilderFactory
				.get("jdbcCursorReaderStep")
				.<Cliente, Cliente>chunk(1)
				.reader(jdbcCursorReader)
				.writer(jdbcCursorWriter)
				.faultTolerant()
				.skip(Exception.class)
				.skipLimit(1)
				.build();
	}
}
