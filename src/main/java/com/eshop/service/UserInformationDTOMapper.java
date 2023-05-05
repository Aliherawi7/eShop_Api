package com.eshop.service;

import com.eshop.constants.APIEndpoints;
import com.eshop.dto.UserInformationDTO;
import com.eshop.model.Role;
import com.eshop.model.UserApp;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;
@Component
public class UserInformationDTOMapper implements Function<UserApp, UserInformationDTO> {
    @Override
    public UserInformationDTO apply(UserApp userApp) {
        return new UserInformationDTO(
                userApp.getId(),
                userApp.getName(),
                userApp.getLastName(),
                APIEndpoints.USER_PICTURE.getValue()+userApp.getId(),
                userApp.getEmail(),
                userApp.getRoles().stream().map(Role::getName).collect(Collectors.toList()),
                userApp.getLocation(),
                0,
                0,
                userApp.isEnabled()
        );
    }
}
