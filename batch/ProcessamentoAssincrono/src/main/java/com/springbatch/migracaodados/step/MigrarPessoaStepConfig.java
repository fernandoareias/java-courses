package com.springbatch.migracaodados.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.migracaodados.dominio.Pessoa;

@Configuration
public class MigrarPessoaStepConfig {
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Value("${migracaoDados.totalRegistros}")
  public Integer totalRegistros;

  @Value("${migracaoDados.gridSize}")
  public Integer gridSize;

  @Autowired
  @Qualifier("transactionManagerApp")
  private PlatformTransactionManager transactionManagerApp;


  @Bean
  public Step migrarPessoaManager(ItemReader<Pessoa> arquivoPessoaReader,
                                  ItemWriter<Pessoa> pessoaItemWriter,
                                  Partitioner partitioner,
                                  TaskExecutor taskExecutor){
    return stepBuilderFactory.get("migrarPessoaStep.manager")
            .partitioner("migrarPessoaStep", partitioner)
            .step(migrarPessoaStep(arquivoPessoaReader, pessoaItemWriter))
            .gridSize(gridSize)
            .taskExecutor(taskExecutor)
            .build();
  }

  @Bean
  public Step migrarPessoaStep(ItemReader<Pessoa> arquivoPessoaReader,
                               ItemWriter<Pessoa> pessoaItemWriter) {
    return ((SimpleStepBuilder<Pessoa, Pessoa>)stepBuilderFactory
        .get("migrarPessoaStep")
        .<Pessoa, Pessoa>chunk(totalRegistros / gridSize)
        .reader(arquivoPessoaReader)
        .writer(pessoaItemWriter)
        .transactionManager(transactionManagerApp))
        .build();
  }
}
