package com.eshop.service;

import com.eshop.model.OrderApp;
import com.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final IPFinderService ipFinderService;

    @Autowired
    public OrderService(OrderRepository orderRepository, IPFinderService ipFinderService) {
        this.orderRepository = orderRepository;
        this.ipFinderService = ipFinderService;
    }

    // find order by id
    public ResponseEntity<?> getOrder(Long id) {
        OrderApp order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("order not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders
    public ResponseEntity<?> getAllOrder() {
        Collection<OrderApp> orders = orderRepository.findAll();
        if (orders.size() > 0) {
            return ResponseEntity.ok(orders);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // save order
    public ResponseEntity<?> addOrder(OrderApp order, HttpServletRequest request) {
        // get user remoteAddress
        String userIPAddress = ipFinderService.getClientIP(request);
        order.setRemoteAddress(userIPAddress);
        orderRepository.save(order);
        return new ResponseEntity<>("order save successfully.", HttpStatus.CREATED);
    }

    // saves orders

    public ResponseEntity<String> addOrders(Collection<OrderApp> orders, HttpServletRequest request) {
        orders.forEach(orderApp -> addOrder(orderApp, request));
        return ResponseEntity.ok("orders save successfully");
    }

    // update order
    public ResponseEntity<?> updateOrder(OrderApp order) {
        orderRepository.save(order);
        return new ResponseEntity<>("order save successfully.", HttpStatus.CREATED);
    }

    // delete an order
    public ResponseEntity<?> deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return new ResponseEntity<>("order deleted successfully", HttpStatus.OK);
    }

    // find all orders with specific userId and ProductID
    public ResponseEntity<?> findAllByUserIdAndProductId(Long userID, Long productId) {
        Collection<OrderApp> orders = orderRepository.findAllByUserIdAndProductId(userID, productId);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }


    // find all orders with specific userId
    public ResponseEntity<?> findAllByUserId(Long userId) {
        Collection<OrderApp> orders = orderRepository.findAllByUserId(userId);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders with specific productID
    public ResponseEntity<?> findAllByProductId(Long productId) {
        Collection<OrderApp> orders = orderRepository.findAllByProductId(productId);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    //find all order in a specific date
    public ResponseEntity<?> findAllByOrderDate(LocalDate orderDate) {
        Collection<OrderApp> orders = orderRepository.findAllByOrderDate(orderDate);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all delivered or not delivered orders in a specific date
    public ResponseEntity<?> findAllByOrderDateAndDelivered(LocalDate date, boolean delivered) {
        Collection<OrderApp> orders = orderRepository.findAllByOrderDateAndDelivered(date, delivered);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders before the specific date
    public ResponseEntity<?> findAllByOrderDateBefore(LocalDate date) {
        Collection<OrderApp> orders = orderRepository.findAllByOrderDateBefore(date);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders after a specific date
    public ResponseEntity<?> findAllByOrderDateAfter(LocalDate date) {
        Collection<OrderApp> orders = orderRepository.findAllByOrderDateAfter(date);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders between two date
    public ResponseEntity<?> findAllByOrderDateBetween(LocalDate startDate, LocalDate endDate) {
        Collection<OrderApp> orders = orderRepository.findAllByOrderDateBetween(startDate, endDate);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all delivered order between two specific date
    public ResponseEntity<?> findAllByOrderDateBetweenAndDelivered(LocalDate startDate, LocalDate endDate, boolean delivered) {
        Collection<OrderApp> orders = orderRepository.findAllByOrderDateBetweenAndDelivered(startDate, endDate, delivered);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find All by userId productId and date
    public ResponseEntity<?> findAllByUserIdAndProductIdAndOrderDateBetween(Long userId, Long productId, LocalDate start, LocalDate end) {
        Collection<OrderApp> orders = orderRepository.findAllByUserIdAndProductIdAndOrderDateBetween(userId, productId, start, end);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }

    // find all orders with specific userId and productId after the specific date
    public ResponseEntity<?> findAllByUserIdAndProductIdAndOrderDateAfter(Long userID, Long productId, LocalDate start) {
        Collection<OrderApp> orders = orderRepository.findAllByUserIdAndProductIdAndOrderDateAfter(userID, productId, start);
        if (orders.size() > 0) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
        }
    }
}
