package com.fernando.cambio.service.repositories;

import com.fernando.cambio.service.models.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long> {
}
