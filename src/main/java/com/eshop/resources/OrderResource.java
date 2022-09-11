package com.eshop.resources;

import com.eshop.model.Order;
import com.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderResource {

    private final OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId){
        return orderService.getOrder(orderId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> addOrder(@RequestBody Order order){
        return orderService.addOrder(order);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        return orderService.deleteOrder(id);
    }

    // find all orders with specific userId and ProductID
    @GetMapping(value = "/find", params = {"userId", "productId"})
    public ResponseEntity<?> findAllByUserIdAndProductId(@RequestParam Map<String, Long> params){
        return orderService.findAllByUserIdAndProductId(params.get("userId"), params.get("productId"));
    }

}
