package com.eshop.dto;

import java.time.LocalDate;

public class UserSignupDTO {
    private String name;
    private String lastName;
    private String email;
    private LocalDate dob;
    private String password;
    private byte[] image;
    private String location;

    public UserSignupDTO() {
    }

    public UserSignupDTO(String name, String lastName, String email,
                         LocalDate dob, String password, byte[] image, String location) {
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
