package com.fernando.batch_processing_with_spring_batch_spring_boot;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
public class BatchProcessingWithSpringBatchSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessingWithSpringBatchSpringBootApplication.class, args);
	}

}
