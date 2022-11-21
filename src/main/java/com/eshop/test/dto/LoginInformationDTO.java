package com.eshop.dto;

public class LoginInformationDTO {
    private String access_token;
    private String refresh_token;
    private UserInformationDTO userInformationDTO;

    public LoginInformationDTO(String access_token, String refresh_token, UserInformationDTO userInformationDTO) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.userInformationDTO = userInformationDTO;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public UserInformationDTO getUserInformationDTO() {
        return userInformationDTO;
    }

    public void setUserInformationDTO(UserInformationDTO userInformationDTO) {
        this.userInformationDTO = userInformationDTO;
    }
}
