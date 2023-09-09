package io.github.fernando.domain.repositories;

import io.github.fernando.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
