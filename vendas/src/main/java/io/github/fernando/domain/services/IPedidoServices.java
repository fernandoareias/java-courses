package io.github.fernando.domain.services;

import io.github.fernando.api.controllers.dto.PedidoDTO;
import io.github.fernando.domain.entities.Pedido;

import java.util.Optional;

public interface IPedidoServices {
    Pedido salvar(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Integer id);
}
