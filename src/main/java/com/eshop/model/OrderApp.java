package com.eshop.model;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long productId;
    private Long userId;
    private LocalDate orderDate;
    private String shippingAddress;
    private int quantity;
    private boolean delivered;

    public Order(){}

    public Order(Long id, Long productId, Long userId, String shippingAddress, int quantity) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.orderDate = LocalDate.now();
        this.shippingAddress = shippingAddress;
        this.quantity = quantity;
        this.delivered = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
