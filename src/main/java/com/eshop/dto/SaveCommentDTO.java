package com.eshop.dto;

public class SaveCommentDTO {
    private long id;
    private long productId;
    private String message;
    private int rate;

    public SaveCommentDTO() {
    }

    public SaveCommentDTO(long id, long productId, String message, int rate) {
        this.id = id;
        this.productId = productId;
        this.message = message;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
