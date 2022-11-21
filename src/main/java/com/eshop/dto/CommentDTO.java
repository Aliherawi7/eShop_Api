package com.eshop.test.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private long commentId;
    private String userName;
    private String message;
    private int rate;
    private LocalDateTime commentDate;
    private byte[] userImage;
    private long likes;
    private long disLikes;

    public CommentDTO() {
    }

    public CommentDTO(long commentId, String userName, String message,
                      int rate, LocalDateTime commentDate, byte[] userImage, long likes, long dislikes) {
        this.commentId = commentId;
        this.userName = userName;
        this.message = message;
        this.rate = rate;
        this.commentDate = commentDate;
        this.userImage = userImage;
        this.likes = likes;
        this.disLikes = dislikes;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDisLikes() {
        return disLikes;
    }

    public void setDisLikes(long disLikes) {
        this.disLikes = disLikes;
    }
}
