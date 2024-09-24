package com.fernando.first_batch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfig {

    @Bean
    public ItemProcessor<Integer, String> impaOuParProcessor() {
        return new FunctionItemProcessor<>(item ->
                item % 2 == 0 ? String.format("Item %s é par", item) : String.format("Item %s é ímpar", item)
        );
    }
}
