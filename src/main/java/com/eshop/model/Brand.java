package com.eshop.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Brand {

    @Id
    @SequenceGenerator(sequenceName = "brand_sequence", name = "brand_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_sequence")
    private Integer id;
    private String name;
    private String logo;
    private LocalDateTime addDate;

    public Brand() {
        addDate = LocalDateTime.now();
    }

    public Brand(Integer id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.addDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}
