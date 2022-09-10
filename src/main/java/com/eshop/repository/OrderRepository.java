package com.eshop.repository;

import com.eshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // find all orders with specific userId and ProductID
    Collection<Order> findAllByUserIdAndProductId(Long userID, Long productId);

    // find all orders with specific userId
    Collection<Order> findAllByUserId(Long userId);

    // find all orders with specific productID
    Collection<Order> findAllByProductId(Long productId);

    //find all order in a specific date
    Collection<Order> findAllByOrderDate(LocalDate orderDate);

    // find all delivered or not delivered orders in a specific date
    Collection<Order> findAllByOrderDateAndDelivered(LocalDate date, boolean delivered);

    // find all orders before the specific date
    Collection<Order> findAllByOrderDateBefore(LocalDate date);

    // find all orders after a specific date
    Collection<Order> findAllByOrderDateAfter(LocalDate date);

    // find all orders between two date
    Collection<Order> findAllByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    // find all delivered order between two specific date
    Collection<Order> findAllByOrderDateBetweenAndDelivered(LocalDate startDate, LocalDate endDate, boolean delivered);

    // find All by userId productId and date
    Collection<Order> findAllByUserIdAndProductIdAndOrderDateBetween(LocalDate start, LocalDate end);

    // find all orders with specific userId and productId after the specific date
    Collection<Order> findAllByUserIdAndProductIdAndOrderDateAfter(LocalDate start);

}
