package com.fernando.course.services;

import com.fernando.course.entities.Order;
import com.fernando.course.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServices {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> findAll()
    {
        return orderRepository.findAll();
    }

    public Order findById(Long id)
    {
        return orderRepository.findById(id).get();
    }


}
