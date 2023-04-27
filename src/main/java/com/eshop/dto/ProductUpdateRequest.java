package com.eshop.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductUpdateRequest {
    private Long productId;
    private String name;
    private String color;
    private MultipartFile imageSide1;
    private MultipartFile imageSide2;
    private MultipartFile imageSide3;
    private String brandName;
    private String category;
    private double price;
    private String descriptions;
    private String size;
    private long quantityInDepot;
    private double rate;
    private double discount;

    public ProductUpdateRequest() {
    }

    public ProductUpdateRequest(long productId,
                                String name,
                                String color,
                                MultipartFile imageSide1,
                                MultipartFile imageSide2,
                                MultipartFile imageSide3,
                                String brandName,
                                String category,
                                double price,
                                String descriptions,
                                String size,
                                long quantityInDepot,
                                double rate,
                                double discount) {
        this.productId = productId;
        this.name = name;
        this.color = color;
        this.imageSide1 = imageSide1;
        this.imageSide2 = imageSide2;
        this.imageSide3 = imageSide3;
        this.brandName = brandName;
        this.category = category;
        this.price = price;
        this.descriptions = descriptions;
        this.size = size;
        this.quantityInDepot = quantityInDepot;
        this.rate = rate;
        this.discount = discount;
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

    public MultipartFile getImageSide1() {
        return imageSide1;
    }

    public void setImageSide1(MultipartFile imageSide1) {
        this.imageSide1 = imageSide1;
    }

    public MultipartFile getImageSide2() {
        return imageSide2;
    }

    public void setImageSide2(MultipartFile imageSide2) {
        this.imageSide2 = imageSide2;
    }

    public MultipartFile getImageSide3() {
        return imageSide3;
    }

    public void setImageSide3(MultipartFile imageSide3) {
        this.imageSide3 = imageSide3;
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

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
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
}
