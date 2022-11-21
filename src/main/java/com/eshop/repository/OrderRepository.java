package com.eshop.test.repository;

import com.eshop.test.model.OrderApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface OrderRepository extends JpaRepository<OrderApp, Long> {

    // find all orders with specific userId and ProductID
    Collection<OrderApp> findAllByUserIdAndProductId(Long userID, Long productId);

    // find all orders with specific userId
    Collection<OrderApp> findAllByUserId(Long userId);

    // find all orders with specific productID
    Collection<OrderApp> findAllByProductId(Long productId);

    //find all order in a specific date
    Collection<OrderApp> findAllByOrderDate(LocalDate orderDate);

    // find all delivered or not delivered orders in a specific date
    Collection<OrderApp> findAllByOrderDateAndDelivered(LocalDate date, boolean delivered);

    // find all orders before the specific date
    Collection<OrderApp> findAllByOrderDateBefore(LocalDate date);

    // find all orders after a specific date
    Collection<OrderApp> findAllByOrderDateAfter(LocalDate date);

    // find all orders between two date
    Collection<OrderApp> findAllByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    // find all delivered order between two specific date
    Collection<OrderApp> findAllByOrderDateBetweenAndDelivered(LocalDate startDate, LocalDate endDate, boolean delivered);

    // find All by userId productId and date
    Collection<OrderApp> findAllByUserIdAndProductIdAndOrderDateBetween(Long userId, Long productId, LocalDate start, LocalDate end);

    // find all orders with specific userId and productId after the specific date
    Collection<OrderApp> findAllByUserIdAndProductIdAndOrderDateAfter(Long userID, Long productId, LocalDate start);

}
