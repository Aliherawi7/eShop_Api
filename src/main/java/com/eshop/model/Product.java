package com.eshop.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Product {
    @Id
    @SequenceGenerator(sequenceName = "product_sequence", name = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long id;
    private String name;
    private String color;
    private String brandName;
    private String category;
    private double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String size;
    private long quantityInDepot;
    private double rate;
    private double discount;
    private LocalDate addedDate;
    private LocalDate updateInDepot;

    // default values
    public Product() {
        this.discount = 0.0;
        this.rate = 4.3;
        this.addedDate = this.updateInDepot = LocalDate.now();
    }

    public Product(Long id, String name, String color,
                   String brandName, String category, double price,
                   String description, String size, long quantityInDepot, double rate, double discount) {
        setId(id);
        ;
        setName(name);
        setColor(color);
        setBrandName(brandName);
        setCategory(category);
        setPrice(price);
        setDescription(description);
        setSize(size);
        setQuantityInDepot(quantityInDepot);
        setRate(rate);
        setDiscount(discount);
        setAddedDate(LocalDate.now());
        setUpdateInDepot(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase().trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName.toLowerCase().trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category.toLowerCase().trim();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toLowerCase().trim();
    }

    public String getSize() {
        return size.toLowerCase().trim();
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getQuantityInDepot() {
        return quantityInDepot;
    }

    public void setQuantityInDepot(long quantityInDepot) {
        this.quantityInDepot = quantityInDepot;
        this.updateInDepot = LocalDate.now();
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

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public LocalDate getUpdateInDepot() {
        return updateInDepot;
    }

    public void setUpdateInDepot(LocalDate updateInDepot) {
        this.updateInDepot = updateInDepot;
    }
}
