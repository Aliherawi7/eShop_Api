package com.eshop.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class OrderApp implements Comparable {

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
    private String location;
    private Double rate;
    private String customerComment;
    private String currency;
    private boolean paid;
    private Double amount;


    public OrderApp() {
        //default properties values
        this.orderDateTime = LocalDateTime.now();
        this.orderDate = orderDateTime.toLocalDate();
        this.delivered = false;
        this.customerComment = "";
        this.currency = "USD";
        this.amount = 0.0;
    }

    public OrderApp(Long id, Long productId, Long userId, String shippingAddress, int quantity, Double amount) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.orderDateTime = LocalDateTime.now();
        this.orderDate = orderDateTime.toLocalDate();
        this.shippingAddress = shippingAddress;
        this.quantity = quantity;
        this.delivered = false;
        this.amount = amount;
    }

    @Override
    public int compareTo(Object o) {
        OrderApp orderApp = (OrderApp) o;
        if (this.orderDate.getYear() > ((OrderApp) o).getOrderDate().getYear()) {
            return this.orderDate.getMonth().compareTo(orderApp.getOrderDate().getMonth());
        } else if (this.orderDate.getYear() < ((OrderApp) o).getOrderDate().getYear()) {
            return -1;
        } else {
            return 0;
        }
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


}
