package com.fernando.migracao.dados.writers;

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
public class JdbcPessoaWriter {

    @Bean
    public JdbcBatchItemWriter<Pessoa> pessoaJdbcBatchItemWriter(
            @Qualifier("appDataSource") DataSource appDataSource
            )
    {
        return new JdbcBatchItemWriterBuilder<Pessoa>()
                .dataSource(appDataSource)
                .sql("INSERT INTO pessoa ( id, nome, email, data_nascimento, idade) VALUES (?, ?, ?, ?, ?)")
                .itemPreparedStatementSetter(itemPreparedStatementSetter())
                .build();
    }

    private ItemPreparedStatementSetter<Pessoa> itemPreparedStatementSetter() {
        return new ItemPreparedStatementSetter<Pessoa>() {
            @Override
            public void setValues(Pessoa item, PreparedStatement ps) throws SQLException {
                ps.setLong(1, item.getId());
                ps.setString(2, item.getNome());
                ps.setString(3, item.getEmail());
                ps.setDate(4, new Date(item.getDataNascimento().getTime()));
                ps.setInt(5, item.getIdade());
            }
        };
    }
}
