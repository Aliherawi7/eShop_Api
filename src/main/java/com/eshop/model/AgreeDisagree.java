package com.eshop.test.model;

import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AgreeDisagree {

    @Id
    @SequenceGenerator(sequenceName = "agree_disagree_sequence", name ="agree_disagree_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agree_disagree_sequence")
    private Long id;
    private boolean agree;
    private boolean disagree;
    private LocalDateTime date;
    private long commentId;
    private long userId;

    public AgreeDisagree() {
    }

    public AgreeDisagree(Long id, boolean agree, boolean disagree, LocalDateTime date, long commentId, long userId) {
        this.id = id;
        this.agree = agree;
        this.disagree = disagree;
        this.date = date;
        this.commentId = commentId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
        setDate(LocalDateTime.now());
    }

    public boolean isDisagree() {
        return disagree;
    }

    public void setDisagree(boolean disagee) {
        this.disagree = disagee;
        setDate(LocalDateTime.now());
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
