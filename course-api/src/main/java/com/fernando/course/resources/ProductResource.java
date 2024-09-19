package com.fernando.course.resources;

import com.fernando.course.entities.Product;
import com.fernando.course.repositories.ProductRepository;
import com.fernando.course.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductServices productServices;


    @GetMapping
    public ResponseEntity<List<Product>> findAll()
    {
        var products = productServices.findAll();

        return ResponseEntity.ok().body(products);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id)
    {
        var product = productServices.findById(id);

        return ResponseEntity.ok().body(product);

    }

}
