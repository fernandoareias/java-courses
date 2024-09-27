package com.fernando.migracao.dados.writers;

import com.fernando.migracao.dados.domain.DadosBancarios;
import com.fernando.migracao.dados.domain.Pessoa;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class JdbcDadosBancariosWriter {
    @Bean
    public JdbcBatchItemWriter<DadosBancarios> dadosBancariosJdbcBatchItemWriter(
            @Qualifier("appDataSource") DataSource appDataSource
    )
    {
        return new JdbcBatchItemWriterBuilder<DadosBancarios>()
                .dataSource(appDataSource)
                .sql("INSERT INTO dados_bancarios ( id, pessoa_id, agencia, conta, banco) VALUES (:id, :pessoaId, :agencia, :conta, :banco)")
                .beanMapped()
                .build();
    }

}
