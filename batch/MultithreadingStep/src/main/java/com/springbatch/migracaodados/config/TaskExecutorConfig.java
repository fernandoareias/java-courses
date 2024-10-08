package com.springbatch.migracaodados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Define o número mínimo de threads que o pool manterá em execução o tempo todo, mesmo que estejam ociosas. Neste caso, 4 threads.
        executor.setCorePoolSize(4);

        // Define a capacidade da fila de tarefas. Se todas as threads ativas estiverem ocupadas e a fila atingir 4 tarefas, novas tarefas serão rejeitadas ou outras estratégias de gerenciamento de fila serão aplicadas.
        executor.setQueueCapacity(4);

        // Define o número máximo de threads que o pool pode expandir para lidar com tarefas extras, se necessário. Aqui, o pool pode ter no máximo 4 threads simultâneas.
        executor.setMaxPoolSize(4);


        executor.setThreadNamePrefix("Multithread - ");

        return executor;
    }
}
