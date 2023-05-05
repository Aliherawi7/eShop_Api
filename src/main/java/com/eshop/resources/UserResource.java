package com.eshop.resources;

import com.eshop.dto.AddRoleToUserDTO;
import com.eshop.dto.EmailAndPasswordDTO;
import com.eshop.dto.UserInformationDTO;
import com.eshop.dto.UserRegistrationRequest;
import com.eshop.model.UserApp;
import com.eshop.security.TestUserWithJWT;
import com.eshop.service.IPFinderService;
import com.eshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@ModelAttribute UserRegistrationRequest userRegistrationRequest, HttpServletRequest request) throws IOException {
        return userService.addUser(userRegistrationRequest);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String jwtEmail = TestUserWithJWT.getUserEmailByJWT(request);
        System.out.println(jwtEmail);
        UserApp user = userService.getUser(jwtEmail);
        UserInformationDTO userDTO = new UserInformationDTO();
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
//        userDTO.setDob(user.getDob());
        userDTO.setLocation(user.getLocation());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(
            @RequestParam("image") MultipartFile file, @RequestParam Map<String, String> params, HttpServletRequest request
    ) throws Exception {
        String email = TestUserWithJWT.getUserEmailByJWT(request);
        UserInformationDTO user = userService.updateUser(email, file, params);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User with email: " + email + " not found!", HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody EmailAndPasswordDTO emailAndPassword) {
        return userService.deleteUser(emailAndPassword.getEmail(), emailAndPassword.getPassword());
    }

    @PostMapping("/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(AddRoleToUserDTO emailAndPassword) {
        return userService
                .addRoleToUser(emailAndPassword.getUserEmail(), emailAndPassword.getRoleName());
    }


}

