package com.eshop.test.dto;

public class SignupInformationDTO {
    private String accessToken;
    private UserInformationDTO userInformationDTO;

    public SignupInformationDTO() { }

    public SignupInformationDTO(String accessToken, UserInformationDTO userInformationDTO) {
        this.accessToken = accessToken;
        this.userInformationDTO = userInformationDTO;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserInformationDTO getUserInformationDTO() {
        return userInformationDTO;
    }

    public void setUserInformationDTO(UserInformationDTO userInformationDTO) {
        this.userInformationDTO = userInformationDTO;
    }
}

