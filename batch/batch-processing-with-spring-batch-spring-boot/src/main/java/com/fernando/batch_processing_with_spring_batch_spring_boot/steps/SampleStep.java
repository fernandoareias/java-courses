package com.fernando.batch_processing_with_spring_batch_spring_boot.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleStep {


    @Bean
    public Step sampleStepMethod(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("firstTaskLet")  Tasklet task){
        return new StepBuilder("sampleStepMethod", jobRepository).tasklet(task, transactionManager).build();
    }
}
