package com.fernando.first_batch.writers;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriterConfig {

    @Bean
    public ItemWriter<String> imprimeImpaParWriter() {
        return itens -> itens.forEach(System.out::println);
    }
}
