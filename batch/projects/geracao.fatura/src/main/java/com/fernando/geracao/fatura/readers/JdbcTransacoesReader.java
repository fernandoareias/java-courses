package com.fernando.geracao.fatura.readers;

import ch.qos.logback.core.net.server.Client;
import com.fernando.geracao.fatura.dominio.CartaoCredito;
import com.fernando.geracao.fatura.dominio.Cliente;
import com.fernando.geracao.fatura.dominio.Transacao;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class JdbcTransacoesReader {

    @Bean
    public JdbcCursorItemReader<Transacao> jdbcCursorItemReader(
            @Qualifier("appDataSource")DataSource dataSource
            ){
        return new JdbcCursorItemReaderBuilder<Transacao>()
                .name("jdbcCursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT *  FROM transacao  JOIN cartao_credito USING (numero_cartao_credito)  ORDER BY numero_cartao_credito;  ")
                .rowMapper(rowMapperTransacao())
                .build();

    }

    private RowMapper<Transacao> rowMapperTransacao() {

        return new RowMapper<Transacao>() {
            @Override
            public Transacao mapRow(ResultSet rs, int rowNum) throws SQLException {
                CartaoCredito cartaoCredito = new CartaoCredito();
                cartaoCredito.setNumeroCartaoCredito(rs.getInt("numero_cartao_credito"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("cliente"));
                cartaoCredito.setCliente(cliente);

                Transacao transacao = new Transacao();
                transacao.setId(rs.getLong("id"));
                transacao.setCartaoCredito(cartaoCredito);
                transacao.setData(rs.getDate("data"));
                transacao.setValor(rs.getDouble("valor"));
                transacao.setDescricao(rs.getString("descricao"));

                return transacao;


            }
        };
    }
}
