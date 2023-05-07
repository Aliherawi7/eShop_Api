package com.eshop.dto;

public class SummeryDTO {
    private Long products;
    private Long orders;
    private long categories;
    private long users;
    private long brands;

    public SummeryDTO() {
    }

    public SummeryDTO(Long products, Long orders, long categories, long users, long brands) {
        this.products = products;
        this.orders = orders;
        this.categories = categories;
        this.users = users;
        this.brands = brands;
    }

    public Long getProducts() {
        return products;
    }

    public void setProducts(Long products) {
        this.products = products;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public long getCategories() {
        return categories;
    }

    public void setCategories(long categories) {
        this.categories = categories;
    }

    public long getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public long getBrands() {
        return brands;
    }

    public void setBrands(long brands) {
        this.brands = brands;
    }
}
