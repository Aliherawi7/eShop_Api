package com.eshop.repository;

import com.eshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface OderRepository extends JpaRepository<Order, Long> {
    Collection<Order> findAllByUserIdAndProductId(Long userID, Long productId);
    Collection<Order> findAllByUserId(Long userId);
    Collection<Order> findAllByProductId(Long productId);
    Collection<Order> findAllByOrderDate(LocalDate orderDate);
    Collection<Order> findAllByOrderDateAndDelivered(LocalDate date, boolean delivered);
    Collection<Order> findAllByDeliveredBefore(LocalDate date);
    Collection<Order> findAllByDeliveredAfter(LocalDate date);
    Collection<Order> findAllByDeliveredBetween(LocalDate startDate, LocalDate endDate);


}
