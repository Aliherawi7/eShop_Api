package com.eshop.dto;

import java.time.LocalDate;
import java.util.List;

public class UserInformationDTO {
    private Long id;
    private String name;
    private String lastName;
    private String image;
    private String email;
    private List<String> roles;
    private String location;
    private LocalDate dob;
    private int totalOrders;
    private double totalSpending;
    private boolean isActive;

    public UserInformationDTO() {
    }

    public UserInformationDTO(Long id, String name, String lastName, String image, String email, List<String> roles, String location, int totalOrders, double totalSpending, boolean isActive) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.image = image;
        this.email = email;
        this.roles = roles;
        this.location = location;
        this.totalOrders = totalOrders;
        this.totalSpending = totalSpending;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        location = location;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
