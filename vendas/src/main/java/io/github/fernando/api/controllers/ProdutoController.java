package io.github.fernando.api.controllers;

import io.github.fernando.domain.entities.Produto;
import io.github.fernando.domain.repositories.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProdutoController {

    private Produtos _produtosRepository;

    public ProdutoController(Produtos _produtosRepository) {
        this._produtosRepository = _produtosRepository;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto obterProdutoPeloId(@PathVariable("id") Integer id){
        return _produtosRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> getAll(Produto filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);

        return _produtosRepository.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto cadastro(Produto newProduct){
         _produtosRepository.save(newProduct);
         return newProduct;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto update(@PathVariable("id") Integer id, Produto updatedProduct){
        return _produtosRepository
                .findById(id)
                .map((p) -> {
                    updatedProduct.setId(p.getId());
                    _produtosRepository.save(updatedProduct);
                    return updatedProduct;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto delete(@PathVariable("id") Integer id)
    {
        return _produtosRepository
                .findById(id)
                .map((p) -> {
                    _produtosRepository.delete(p);
                    return p;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
}
