package com.eshop.dto;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductDTO {
    private Long productId;
    private String name;
    private String color;
    private ArrayList<String> images;
    private String brandName;
    private String category;
    private double price;
    @Column(columnDefinition = "TEXT")
    private ArrayList<String> details;
    private String size;
    private long quantityInDepot;
    private double rate;
    private double discount;
    private int reviews;

    public ProductDTO() {
    }

    public ProductDTO(Long productId, String name, String color,
                      ArrayList<String> images, String brandName, String category,
                      double price, ArrayList<String> details,
                      String size, long quantityInDepot, double rate, double discount, int reviews) {
        this.productId = productId;
        this.name = name;
        this.color = color;
        this.images = images;
        this.brandName = brandName;
        this.category = category;
        this.price = price;
        this.details = details;
        this.size = size;
        this.quantityInDepot = quantityInDepot;
        this.rate = rate;
        this.discount = discount;
        this.reviews = reviews;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<String> details) {
        this.details = details;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getQuantityInDepot() {
        return quantityInDepot;
    }

    public void setQuantityInDepot(long quantityInDepot) {
        this.quantityInDepot = quantityInDepot;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
