package com.eshop.test.dto;

public class AddRoleToUserDTO {
    private String userEmail;
    private String RoleName;

    public AddRoleToUserDTO(){}

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
