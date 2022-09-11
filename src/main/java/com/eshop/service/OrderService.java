package com.eshop.service;

import com.eshop.model.Order;
import com.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.nodes.CollectionNode;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
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

    // find all orders with specific userId and ProductID
    public ResponseEntity<?> findAllByUserIdAndProductId(Long userID, Long productId){
        Collection<Order> orders = orderRepository.findAllByUserIdAndProductId(userID, productId);
        if(orders.size() > 0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }


    // find all orders with specific userId
    public ResponseEntity<?> findAllByUserId(Long userId){
        Collection<Order> orders = orderRepository.findAllByUserId(userId);
        if(orders.size()>0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders with specific productID
    public ResponseEntity<?> findAllByProductId(Long productId){
        Collection<Order> orders = orderRepository.findAllByProductId(productId);
        if(orders.size()>0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    //find all order in a specific date
    public ResponseEntity<?> findAllByOrderDate(LocalDate orderDate){
        Collection<Order> orders = orderRepository.findAllByOrderDate(orderDate);
        if(orders.size()>0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all delivered or not delivered orders in a specific date
    public ResponseEntity<?> findAllByOrderDateAndDelivered(LocalDate date, boolean delivered){
        Collection<Order> orders = orderRepository.findAllByOrderDateAndDelivered(date, delivered);
        if(orders.size() > 0){
            return  new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders before the specific date
    public ResponseEntity<?> findAllByOrderDateBefore(LocalDate date){
        Collection<Order> orders = orderRepository.findAllByOrderDateBefore(date);
        if(orders.size() > 0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders after a specific date
    public ResponseEntity<?> findAllByOrderDateAfter(LocalDate date){
        Collection<Order> orders = orderRepository.findAllByOrderDateAfter(date);
        if(orders.size() > 0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders between two date
    public ResponseEntity<?> findAllByOrderDateBetween(LocalDate startDate, LocalDate endDate){
        Collection<Order> orders = orderRepository.findAllByOrderDateBetween(startDate, endDate);
        if(orders.size() > 0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all delivered order between two specific date
    public ResponseEntity<?> findAllByOrderDateBetweenAndDelivered(LocalDate startDate, LocalDate endDate, boolean delivered){
        Collection<Order> orders = orderRepository.findAllByOrderDateBetweenAndDelivered(startDate, endDate, delivered);
        if(orders.size() > 0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find All by userId productId and date
    public ResponseEntity<?> findAllByUserIdAndProductIdAndOrderDateBetween(Long userId, Long productId, LocalDate start, LocalDate end){
        Collection<Order> orders = orderRepository.findAllByUserIdAndProductIdAndOrderDateBetween(userId, productId, start, end);
        if(orders.size() > 0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders with specific userId and productId after the specific date
    public ResponseEntity<?> findAllByUserIdAndProductIdAndOrderDateAfter(Long userID, Long productId, LocalDate start){
        Collection<Order> orders = orderRepository.findAllByUserIdAndProductIdAndOrderDateAfter(userID, productId, start);
        if(orders.size() >0){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }
}
