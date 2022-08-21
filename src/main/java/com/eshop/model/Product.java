package com.eshop.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data @NoArgsConstructor @RequiredArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(sequenceName = "product_sequence", name = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Integer id;
    private String name;
    private String color;
    private String imgUrl;
    private String brandName;
    private String category;
    private Double price;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    private LocalDate productionDate;
    private String size;
}

