package com.eshop.dto;

import java.util.List;

public class UserInformationDTO {
    private String name;
    private String lastName;
    private String imgUrl;
    private String email;
    private List<String> roles;

    public UserInformationDTO() {
    }

    public UserInformationDTO(String name, String lastName, String imgUrl, String email, List<String> roles) {
        this.name = name;
        this.lastName = lastName;
        this.imgUrl = imgUrl;
        this.email = email;
        this.roles = roles;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
