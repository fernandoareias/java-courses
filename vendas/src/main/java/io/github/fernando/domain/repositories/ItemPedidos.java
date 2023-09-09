package io.github.fernando.domain.repositories;

import io.github.fernando.domain.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidos extends JpaRepository<ItemPedido, Integer> {
}
