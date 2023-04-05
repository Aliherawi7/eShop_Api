package com.eshop.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductRegisterationRequest {
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

    public ProductRegisterationRequest() {
    }

    public ProductRegisterationRequest(String name,
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
        this.reviews = reviews;
    }
}
