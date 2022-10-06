package com.eshop.resources;

import com.eshop.dto.AddRoleToUserDTO;
import com.eshop.dto.EmailAndPasswordDTO;
import com.eshop.dto.UserSignupDTO;
import com.eshop.model.Role;
import com.eshop.model.UserApp;
import com.eshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<?> addUser(@ RequestBody UserSignupDTO userInfo, HttpServletRequest request){
        return userService.addUser(userInfo);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        email = email.trim().toLowerCase();
        UserApp user = userService.getUser(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with email: " + email + " not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody EmailAndPasswordDTO emailAndPassword) {
        return userService.deleteUser(emailAndPassword.getEmail(), emailAndPassword.getPassword());
    }

   @PostMapping("/addRoleToUser")
   public ResponseEntity<?> addRoleToUser(AddRoleToUserDTO emailAndPassword){
        return userService
                .addRoleToUser(emailAndPassword.getUserEmail(), emailAndPassword.getRoleName());
   }



}

