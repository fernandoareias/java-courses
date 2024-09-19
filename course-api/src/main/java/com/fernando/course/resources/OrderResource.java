package com.fernando.course.resources;


import com.fernando.course.entities.Order;
import com.fernando.course.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderServices orderServices;

    @GetMapping
    public ResponseEntity<List<Order>> findAll()
    {
        var users = orderServices.findAll();

        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id)
    {
        var order = orderServices.findById(id);

        return ResponseEntity.ok().body(order);
    }


}
