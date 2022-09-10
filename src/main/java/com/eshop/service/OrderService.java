package com.eshop.service;

import com.eshop.model.Order;
import com.eshop.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    // find order by id
    public ResponseEntity<?> getOrder(Long id){
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null){
            return new ResponseEntity<>(order, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("order not found with id: "+id, HttpStatus.NOT_FOUND);
        }
    }
}
