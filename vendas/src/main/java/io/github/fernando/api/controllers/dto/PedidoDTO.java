package io.github.fernando.api.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO
{
    private Integer clienteId;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;
}

