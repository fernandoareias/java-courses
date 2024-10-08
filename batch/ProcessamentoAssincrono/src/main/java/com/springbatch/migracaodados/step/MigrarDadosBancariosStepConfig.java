package com.springbatch.migracaodados.step;

import com.springbatch.migracaodados.dominio.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.migracaodados.dominio.DadosBancarios;

@Configuration
public class MigrarDadosBancariosStepConfig {
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
  public Step migrarDadosBancariosManager(ItemReader<DadosBancarios> dadosBancariosItemReader,
                                  ItemWriter<DadosBancarios> dadosBancariosItemWriter,
                                  Partitioner partitioner,
                                  TaskExecutor taskExecutor){
    return stepBuilderFactory.get("migrarDadosBancarios.manager")
            .partitioner("migrarDadosBancariosStep", partitioner)
            .step(migrarDadosBancariosStep(dadosBancariosItemReader, dadosBancariosItemWriter))
            .gridSize(gridSize)
            .taskExecutor(taskExecutor)
            .build();
  }

  @Bean
  public Step migrarDadosBancariosStep(ItemReader<DadosBancarios> arquivoDadosBancariosReader,
      ItemWriter<DadosBancarios> bancoDadosBancariosWriter) {
    return stepBuilderFactory
        .get("migrarDadosBancariosStep")
        .<DadosBancarios, DadosBancarios>chunk(totalRegistros/gridSize)
        .reader(arquivoDadosBancariosReader)
        .writer(bancoDadosBancariosWriter)
        .transactionManager(transactionManagerApp)
        .build();
  }
}
