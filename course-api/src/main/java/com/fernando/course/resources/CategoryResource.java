package com.fernando.course.resources;

import com.fernando.course.entities.Category;
import com.fernando.course.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryServices categoryServices;


    @GetMapping
    public ResponseEntity<List<Category>> findAll()
    {
        var categories = categoryServices.findAll();

        if(categories == null) return ResponseEntity.ok().body(Collections.emptyList());

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id)
    {
        var category = categoryServices.findById(id);

        if(category == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(category);
    }

}
