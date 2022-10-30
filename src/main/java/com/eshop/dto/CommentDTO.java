package com.eshop.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private String userName;
    private String comment;
    private int rate;
    private LocalDateTime commentDate;
    private byte[] userImage;

    public CommentDTO() {
    }

    public CommentDTO(String userName, String comment, int rate, LocalDateTime commentDate, byte[] userImage) {
        this.userName = userName;
        this.comment = comment;
        this.rate = rate;
        this.commentDate = commentDate;
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }
}
