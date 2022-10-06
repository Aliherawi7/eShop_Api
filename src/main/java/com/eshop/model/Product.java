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
    @Lob
    private byte[] image;
    private String brandName;
    private String category;
    private Double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate productionDate;
    private String size;
    private Long quantityInDepot;

    public Product() {
    }

    public Product(Long id, String name, String color, byte[] image,
                   String brandName, String category, Double price,
                   String description, LocalDate productionDate, String size, Long quantityInDepot) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.image = image;
        this.brandName = brandName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.productionDate = productionDate;
        this.size = size;
        this.quantityInDepot = quantityInDepot;
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
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getQuantityInDepot() {
        return quantityInDepot;
    }

    public void setQuantityInDepot(Long quantityInDepot) {
        this.quantityInDepot = quantityInDepot;
    }
}
