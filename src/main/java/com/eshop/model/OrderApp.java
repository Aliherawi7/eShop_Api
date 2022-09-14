package com.eshop.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class OrderApp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long productId;
    private Long userId;
    private LocalDateTime orderDateTime;
    private LocalDate orderDate;
    private String shippingAddress;
    private int quantity;
    private boolean delivered;
    private LocalDateTime deliveredDate;
    private String remoteAddress;
    private String customerComment;
    private String currency;

    public OrderApp(){
        //default properties values
        this.orderDateTime = LocalDateTime.now();
        this.orderDate = orderDateTime.toLocalDate();
        this.delivered = false;
        this.customerComment = "";
        this.currency = "USD";
    }

    public OrderApp(Long id, Long productId, Long userId, String shippingAddress, int quantity) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.orderDateTime = LocalDateTime.now();
        this.orderDate = orderDateTime.toLocalDate();
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

    public LocalDate getOrderDate(){ return orderDate; }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }
    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
