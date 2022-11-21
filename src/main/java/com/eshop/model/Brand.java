package com.eshop.test.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Lob
    private byte[] logo;
    private LocalDateTime addDate;

    public Brand() {
        addDate = LocalDateTime.now();
    }

    public Brand(Integer id, String name, byte[] logo) {
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }
}
