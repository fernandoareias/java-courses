package com.springbatch.demonstrativoorcamentario.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Configuration
public class DemonstrativoOrcamentarioReaderConfig {
	@StepScope
	@Bean
	public MultiResourceItemReader<GrupoLancamento> demonstrativoOrcamentarioReader(
			GrupoLancamentoReader grupoLancamentoReader) throws IOException {

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] arquivosLancamento = resolver.getResources("classpath:files/lancamentos*.txt");

		return new MultiResourceItemReaderBuilder<GrupoLancamento>()
				.name("demonstrativoOrcamentarioReader")
				.resources(arquivosLancamento)
				.delegate(grupoLancamentoReader)
				.build();
	}
}
