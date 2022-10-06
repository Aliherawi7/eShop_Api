package com.eshop.dto;

import java.util.List;

public class UserInformationDTO {
    private String name;
    private String lastName;
    private byte[] image;
    private String email;
    private List<String> roles;

    public UserInformationDTO() {
    }

    public UserInformationDTO(String name, String lastName, byte[] image, String email, List<String> roles) {
        this.name = name;
        this.lastName = lastName;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
