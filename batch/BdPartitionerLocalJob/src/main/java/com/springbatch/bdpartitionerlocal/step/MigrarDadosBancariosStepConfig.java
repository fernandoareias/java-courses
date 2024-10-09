package com.springbatch.bdpartitionerlocal.step;

import com.springbatch.bdpartitionerlocal.dominio.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.bdpartitionerlocal.dominio.DadosBancarios;

@Configuration
public class MigrarDadosBancariosStepConfig {
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  @Qualifier("transactionManagerApp")
  private PlatformTransactionManager transactionManagerApp;


  @Bean
  public Step migrarDadosBancariosStepManager(
          ItemReader<DadosBancarios> dadosBancariosReader,
          ItemWriter<DadosBancarios> dadosBancariosWriter,
          @Qualifier("pessoaPartitioner") Partitioner partitioner,
          TaskExecutor taskExecutor
  ){
    return stepBuilderFactory
            .get("migrarDadosBancariosStepManager")
            .partitioner("migrarPessoa.manager", partitioner)
            .taskExecutor(taskExecutor)
            .gridSize(10)
            .step(migrarDadosBancariosStep(dadosBancariosReader, dadosBancariosWriter))
            .build();
  }

  public Step migrarDadosBancariosStep(
      ItemReader<DadosBancarios> dadosBancariosReader,
      ItemWriter<DadosBancarios> dadosBancariosWriter) {
    return stepBuilderFactory
        .get("migrarDadosBancariosStep")
        .<DadosBancarios, DadosBancarios>chunk(20000)
        .reader(dadosBancariosReader)
        .writer(dadosBancariosWriter)
        .transactionManager(transactionManagerApp)
        .build();
  }
}
