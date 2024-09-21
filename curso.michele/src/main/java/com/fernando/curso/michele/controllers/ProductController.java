package com.fernando.curso.michele.controllers;

import com.fernando.curso.michele.dtos.requests.CreateProductRequest;
import com.fernando.curso.michele.dtos.requests.UpdateProductRequest;
import com.fernando.curso.michele.models.ProductModel;
import com.fernando.curso.michele.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public ResponseEntity<List<ProductModel>> getProducts(){
        var products = productRepository.findAll();

        if(products.isEmpty())
            return ResponseEntity.ok().build();

        for(var product : products)
            product.add(linkTo(methodOn(ProductController.class).getById(product.getIdProduct())).withSelfRel());


        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getById(@PathVariable(value = "id") UUID id)
    {
        var product = productRepository.findById(id);

        if(product.isEmpty())
            return ResponseEntity.notFound().build();

        product.get().add(linkTo(methodOn(ProductController.class).getProducts()).withRel("Products List"));

        return ResponseEntity.ok().body(product.get());
    }

    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid CreateProductRequest request){

        var productModel = new ProductModel();
        BeanUtils.copyProperties(request, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));

    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid UpdateProductRequest request
    ){
        var product = productRepository.findById(id);

        if(product.isEmpty())
            return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(request, product);

        return ResponseEntity.ok().body(product.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") UUID id){
        var product = productRepository.findById(id);

        if(product.isEmpty())
            return ResponseEntity.notFound().build();

        productRepository.delete(product.get());

        return ResponseEntity.noContent().build();
    }
}
