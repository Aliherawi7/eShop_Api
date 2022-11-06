package com.eshop.dto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class ProductSidesImages {
    @Id
    @SequenceGenerator(sequenceName = "product_sequence", name = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long id;
    private long productId;
    @Lob
    private byte[] side1;
    @Lob
    private byte[] side2;
    @Lob
    private byte[] side3;

    public ProductSidesImages() {
    }

    public ProductSidesImages(Long id, long productId, byte[] side1, byte[] side2, byte[] side3) {
        this.id = id;
        this.productId = productId;
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Collection<byte[]> getAllSidesImage(){
        ArrayList<byte[]> sides = new ArrayList<>();
        sides.add(getSide1());
        sides.add(getSide2());
        sides.add(getSide3());
        return sides;
    }

    public byte[] getSide1() {
        return side1;
    }

    public void setSide1(byte[] side1) {
        this.side1 = side1;
    }

    public byte[] getSide2() {
        return side2;
    }

    public void setSide2(byte[] side2) {
        this.side2 = side2;
    }

    public byte[] getSide3() {
        return side3;
    }

    public void setSide3(byte[] side3) {
        this.side3 = side3;
    }
}
