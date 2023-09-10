package io.github.fernando.application.services;

import io.github.fernando.api.controllers.dto.ItemPedidoDTO;
import io.github.fernando.api.controllers.dto.PedidoDTO;
import io.github.fernando.domain.entities.ItemPedido;
import io.github.fernando.domain.entities.Pedido;
import io.github.fernando.domain.enums.EPedidoStatus;
import io.github.fernando.domain.exceptions.DomainException;
import io.github.fernando.domain.repositories.Clientes;
import io.github.fernando.domain.repositories.ItemPedidos;
import io.github.fernando.domain.repositories.Pedidos;
import io.github.fernando.domain.repositories.Produtos;
import io.github.fernando.domain.services.IPedidoServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServices implements IPedidoServices
{
    private final Pedidos _pedidoRepository;
    private final Clientes _clientesRepository;
    private final Produtos _produtosRepository;
    private final ItemPedidos _itemsPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        var idCliente = pedidoDTO.getClienteId();
        var cliente = _clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new DomainException("Cliente code invalid!"));

        var pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(EPedidoStatus.REALIZADO);

        var itemsPedido = converterItems(pedido, pedidoDTO.getItems());
        _pedidoRepository.save(pedido);
        _itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return _pedidoRepository.findByIdFetchItens(id);
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty())
            throw new DomainException("Order items empty!");

        return items
                .stream()
                .map((dto) -> {
                    var produto = _produtosRepository
                            .findById(pedido.getId())
                            .orElseThrow(() -> new DomainException("Pedido not found"));

                    var itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
