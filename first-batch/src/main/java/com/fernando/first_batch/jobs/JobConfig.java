package com.fernando.first_batch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    @Bean
    public Job imprimeImpaParJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("imprime-impa-par-job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
