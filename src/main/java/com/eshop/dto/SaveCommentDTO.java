package com.eshop.dto;

public class SaveCommentDTO {
    private long id;
    private long productId;
    private String comment;
    private int rate;

    public SaveCommentDTO() {
    }

    public SaveCommentDTO(long id, long productId, String comment, int rate) {
        this.id = id;
        this.productId = productId;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
