package com.eshop.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @SequenceGenerator(sequenceName = "comment_sequence", name = "comment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    private Long id;
    private long productId;
    private long userId;
    private LocalDateTime commentDate;
    private String message;
    private int rate;

    public Comment() {
        this.message = "";
        commentDate = LocalDateTime.now();
        rate = 4;
    }

    public Comment(Long id, long productId, long userId, LocalDateTime commentDate, String message, int rate) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.commentDate = commentDate;
        this.message = message;
        this.rate = rate;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String comment) {
        this.message = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
