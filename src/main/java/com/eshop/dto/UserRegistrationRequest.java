package com.eshop.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserRegistrationRequest {
    private String name;
    private String lastName;
    private String email;
    private String dob;
    private String password;
    private MultipartFile image;
    private String location;

    public UserRegistrationRequest() {
    }

    public UserRegistrationRequest(String name, String lastName, String email,
                                   String dob, String password, MultipartFile image, String location) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.image = image;
        this.location = location;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        if(dob.contains(",")){
            return dob.split(",")[0];
        }
        return dob;
    }

    public void setDob(String dob) {
        if(dob.contains(",")){
            this.dob = dob.split(",")[0];
            return;
        }
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
