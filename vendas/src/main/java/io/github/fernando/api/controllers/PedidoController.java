package io.github.fernando.api.controllers;

import io.github.fernando.api.controllers.dto.InformacaoItemPedidoDTO;
import io.github.fernando.api.controllers.dto.InformacoesPedidoDTO;
import io.github.fernando.api.controllers.dto.ItemPedidoDTO;
import io.github.fernando.api.controllers.dto.PedidoDTO;
import io.github.fernando.domain.entities.ItemPedido;
import io.github.fernando.domain.entities.Pedido;
import io.github.fernando.domain.services.IPedidoServices;
import org.h2.schema.Domain;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class PedidoController
{
    private IPedidoServices _pedidoServices;

    public PedidoController(IPedidoServices _pedidoServices) {
        this._pedidoServices = _pedidoServices;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO){
        var pedidoRealizado =  _pedidoServices.salvar(pedidoDTO);
        return pedidoRealizado.getId();
    }


    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable("id") Integer id){
        return _pedidoServices.obterPedidoCompleto(id)
                .map(this::converter)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @PatchMapping
    public void updateStatus(){

    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getName())
                .total(pedido.getTotal())
                .items(converter(pedido.getItens()))
                .status(pedido.getStatus().name())
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens)
    {
        if(CollectionUtils.isEmpty(itens))
            return Collections.emptyList();

        return itens
                .stream()
                .map(
                        i -> InformacaoItemPedidoDTO
                                .builder()
                                .descricaoProduto(i.getProduto().getDescricao())
                                .precoUnitario(i.getProduto().getPreco())
                                .quantidade(i.getQuantidade())
                                .build()
                ).collect(Collectors.toList());
    }
}
