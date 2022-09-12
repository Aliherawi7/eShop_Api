package com.eshop.resources;

import com.eshop.model.Order;
import com.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
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

    @PostMapping("/saveAll")
    public ResponseEntity<?> addOrders(@RequestBody Collection<Order> orders){
        return orderService.addOrders(orders);
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

    // find all orders with specific userId
    @GetMapping(value = "/find", params = {"userId"})
    public ResponseEntity<?> findAllByUserId(@RequestParam Long userId){
        return orderService.findAllByUserId(userId);
    }

    // find all orders with specific productID
    @GetMapping(value = "/find", params = {"productId"})
    public ResponseEntity<?> findAllByProductId(@RequestParam Long productId){
        return orderService.findAllByProductId(productId);
    }

    //find all order in a specific date
    @GetMapping(value = "/find", params = {"orderDate"})
    public ResponseEntity<?> findAllByOrderDate(@RequestParam LocalDate orderDate){
        return orderService.findAllByOrderDate(orderDate);
    }

    // find all delivered or not delivered orders in a specific date
    @GetMapping(value = "/find", params = {"orderDate", "delivered"})
    public ResponseEntity<?> findAllByOrderDateAndDelivered(@RequestParam Map<String, String> params){
        LocalDate date = LocalDate.parse(params.get("orderDate"));
        boolean delivered = Boolean.parseBoolean(params.get("delivered"));
        return orderService.findAllByOrderDateAndDelivered(date, delivered);
    }

    // find all orders before the specific date
    @GetMapping(value = "/find", params = {"beforeDate"})
    public ResponseEntity<?> findAllByOrderDateBefore(@RequestParam LocalDate beforeDate){
        return orderService.findAllByOrderDateBefore(beforeDate);
    }

    // find all orders after a specific date
    @GetMapping(value = "/find", params = {"afterDate"})
    public ResponseEntity<?> findAllByOrderDateAfter(@RequestParam LocalDate afterDate){
        return orderService.findAllByOrderDateAfter(afterDate);
    }

    // find all orders between two date
    @GetMapping(value = "/find", params = {"startDate", "endDate"})
    public ResponseEntity<?> findAllByOrderDateBetween(@RequestParam Map<String, LocalDate> params){
        return orderService.findAllByOrderDateBetween(params.get("startDate"), params.get("endDate"));
    }

    // find all delivered order between two specific date
    @GetMapping(value="/find", params = {"startDate", "endDate", "delivered"})
    public ResponseEntity<?> findAllByOrderDateBetweenAndDelivered(@RequestParam Map<String, String> params){
        LocalDate start = LocalDate.parse(params.get("startDate"));
        LocalDate end = LocalDate.parse(params.get("endDate"));
        boolean delivered = Boolean.parseBoolean(params.get("delivered"));
        return orderService.findAllByOrderDateBetweenAndDelivered(start, end, delivered);
    }

    // find All by userId productId and date
    @GetMapping(value="/find", params = {"userId", "productId", "startDate", "endDate"})
    public ResponseEntity<?> findAllByUserIdAndProductIdAndOrderDateBetween(@RequestParam Map<String, String> params){
        Long userId = Long.parseLong(params.get("userId"));
        Long productId = Long.parseLong(params.get("productId"));
        LocalDate start = LocalDate.parse(params.get("startDate"));
        LocalDate end = LocalDate.parse(params.get("endDate"));
        return orderService.findAllByUserIdAndProductIdAndOrderDateBetween(userId, productId, start, end);
    }

    // find all orders with specific userId and productId after the specific date
    @GetMapping(value="/find", params = {"userId", "productId", "dateAfter"})
    public ResponseEntity<?> findAllByUserIdAndProductIdAndOrderDateAfter(@RequestParam Map<String, String> params){
        Long userId = Long.parseLong(params.get("userId"));
        Long productId = Long.parseLong(params.get("productId"));
        LocalDate dateAfter = LocalDate.parse(params.get("dateAfter"));
        return orderService.findAllByUserIdAndProductIdAndOrderDateAfter(userId, productId, dateAfter);
    }




}
