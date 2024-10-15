package com.fernando.batch_processing_with_spring_batch_spring_boot.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondTaskletConfig {
    @Bean
    public Tasklet secondTaskLet(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                System.out.println("Processou segunda task");

                return RepeatStatus.FINISHED;
            }
        };
    }
}
