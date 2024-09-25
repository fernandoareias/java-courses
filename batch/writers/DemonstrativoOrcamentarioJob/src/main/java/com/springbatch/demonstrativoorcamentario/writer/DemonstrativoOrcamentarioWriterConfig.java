package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.*;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;
import org.springframework.core.io.Resource;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {


	@StepScope
	@Bean
	public MultiResourceItemWriter<GrupoLancamento> multiDemonstrativoOrcamentarioWrite(
			@Value("file:./Users/f.areias/Desktop/java-courses/batch/writers/DemonstrativoOrcamentarioJob/src/main/resources/files/demonstrativoOrcamentario") Resource outputDirectory, // Modificado para apontar para um diretório
			FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter
	) {
		return new MultiResourceItemWriterBuilder<GrupoLancamento>()
				.name("multiDemonstrativoOrcamentarioWrite")
				.resource(outputDirectory) // O diretório onde os arquivos serão criados
				.delegate(demonstrativoOrcamentarioWriter)
				.resourceSuffixCreator(suffixCreator()) // Para gerar os arquivos com sufixos como 1, 2, 3...
				.itemCountLimitPerResource(1) // Limite de itens por arquivo
				.build();
	}


	private ResourceSuffixCreator suffixCreator() {
		return new ResourceSuffixCreator() {
			@Override
			public String getSuffix(int i) {
				return i + ".txt";
			}
		};
	}


	@Bean
	public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter(
			@Value("classpath:files/demonstrativoOrcamentario.txt") Resource arquivoCliente,
			DemonstrativoOrcamentarioRodape rodape
	)
	{
		return new FlatFileItemWriterBuilder<GrupoLancamento>()
				.name("demonstrativoOrcamentarioWriter")
				.resource(arquivoCliente)
				.lineAggregator(lineAggregator())
				.headerCallback(cabecalhoCallback())
				.footerCallback(rodape)
				.build();

	}

	private FlatFileHeaderCallback cabecalhoCallback() {
		return new FlatFileHeaderCallback() {
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.append("\n");
				writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
				writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t HORA: %s", new SimpleDateFormat("HH:MM").format(new Date())));
				writer.append(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO"));
				writer.append(String.format("----------------------------------------------------------------------------"));
				writer.append(String.format("CODIGO NOME VALOR"));
				writer.append(String.format("\t Data Descricao Valor"));
				writer.append(String.format("----------------------------------------------------------------------------"));

			}
		};
	}

	private LineAggregator<GrupoLancamento> lineAggregator() {
		return new LineAggregator<GrupoLancamento>() {
			@Override
			public String aggregate(GrupoLancamento grupoLancamento) {
				String formatoGrupoLancamento = String.format("[%d] %s - %s\n", grupoLancamento.getCodigoNaturezaDespesa(),
						grupoLancamento.getDescricaoNaturezaDespesa(),
						NumberFormat.getCurrencyInstance().format(grupoLancamento.getTotal()));

				StringBuilder sb = new StringBuilder();

				for (Lancamento lancamento : grupoLancamento.getLancamentos()) {
					sb.append(String.format("\t [%s] %s - %s\n", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
							NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
				}

				String formatLancamento = sb.toString();

				return formatoGrupoLancamento + formatLancamento;
			}
		};
	}
}
