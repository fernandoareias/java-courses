package com.fernando.batch_processing_with_spring_batch_spring_boot.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleJob {



    @Bean
    public Job sampleJobmethod(JobRepository jobRepository,
                               @Qualifier("sampleStepMethod") Step step,
                               @Qualifier("secondTaskLet") Step secondStep){
        return new JobBuilder( "sampleJobmethod", jobRepository)
                .start(step)
                .next(secondStep)
                .build();
    }
}
