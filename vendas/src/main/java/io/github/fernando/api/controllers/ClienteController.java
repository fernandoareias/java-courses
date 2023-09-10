package io.github.fernando.api.controllers;

import ch.qos.logback.core.net.server.Client;
import io.github.fernando.domain.entities.Cliente;
import io.github.fernando.domain.repositories.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClienteController {
    private Clientes _clientesRepository;

    public ClienteController(Clientes _clientesRepository) {
        this._clientesRepository = _clientesRepository;
    }


    @GetMapping
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        List<Cliente> listaCliente = _clientesRepository.findAll(example);

        return ResponseEntity.ok(listaCliente);
    }


    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente getClienteById(@PathVariable("id") Integer id){

        return _clientesRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente not found"));

    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente createCliente(@RequestBody Cliente cliente)
    {
        return _clientesRepository.save(cliente);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Cliente updateCliente(@PathVariable("id") Integer id, @RequestBody Cliente cliente){
        return _clientesRepository
                .findById(id)
                .map( clienteExist -> {
                    cliente.setId(clienteExist.getId());
                    _clientesRepository.save(cliente);
                    return cliente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Cliente deleteCliente(@PathVariable("id") Integer id){
        return _clientesRepository.findById(id)
                .map((cliente) -> {
                    _clientesRepository.delete(cliente);
                    return cliente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

}
