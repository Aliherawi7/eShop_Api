package com.eshop.dto;

public class UserInformationDTO {
    private String name;
    private String lastName;
    private String imgUrl;
    private String email;

    public UserInformationDTO() {
    }

    public UserInformationDTO(String name, String lastName, String imgUrl, String email) {
        this.name = name;
        this.lastName = lastName;
        this.imgUrl = imgUrl;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
