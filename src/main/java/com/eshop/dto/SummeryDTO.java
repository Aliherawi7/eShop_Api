package com.eshop.dto;

public class SummeryDTO {
    private Long products;
    private Long orders;
    private int categories;
    private int users;
    private int brands;

    public SummeryDTO() {
    }

    public SummeryDTO(Long products, Long orders, int categories, int users, int brands) {
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

    public int getCategories() {
        return categories;
    }

    public void setCategories(int categories) {
        this.categories = categories;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getBrands() {
        return brands;
    }

    public void setBrands(int brands) {
        this.brands = brands;
    }
}
