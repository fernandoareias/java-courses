package io.github.fernando.domain.repositories;

import io.github.fernando.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos  extends JpaRepository<Pedido, Integer> {
}
