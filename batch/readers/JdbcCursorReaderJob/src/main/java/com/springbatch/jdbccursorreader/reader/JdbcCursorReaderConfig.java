package com.springbatch.jdbccursorreader.reader;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.jdbccursorreader.dominio.Cliente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class JdbcCursorReaderConfig {
	@Bean
	public JdbcCursorItemReader<Cliente> jdbcCursorReader(@Qualifier("appDataSource")DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Cliente>()
				.name("jdbcCursorReader")
				.dataSource(dataSource)
				.sql("select * from cliente")
				.rowMapper(rowMapper())
				.build();
	}

	private RowMapper<Cliente> rowMapper()
	{
		return new BeanPropertyRowMapper<Cliente>(){

			@Override
			public Cliente mapRow(ResultSet rs, int rowNumber) throws SQLException {
				if(rs.getRow() == 11)
					throw new SQLException(String.format("Encerrando a execucacao - Cliente invalido %s", rs.getString("email")));

				return clienteRowMapper(rs);
			}

			private Cliente clienteRowMapper(ResultSet rs) throws SQLException {
				Cliente cliente = new Cliente();
				cliente.setNome(rs.getString("nome"));
				cliente.setSobrenome(rs.getString("sobrenome"));
				cliente.setIdade(rs.getString("idade"));
				cliente.setEmail(rs.getString("email"));

				return cliente;
			}

		};
	}


}
