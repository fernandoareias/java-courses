package com.fernando.course.services;

import com.fernando.course.entities.Category;
import com.fernando.course.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }

    public Category findById(Long id)
    {
        return categoryRepository.findById(id).get();
    }
}
