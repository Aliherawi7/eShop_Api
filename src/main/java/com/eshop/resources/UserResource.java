package com.eshop.resources;

import com.eshop.dto.EmailAndPasswordDTO;
import com.eshop.model.User;
import com.eshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;
    public UserResource(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){
        email = email.trim().toLowerCase();
        User user = userService.getUser(email);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("User with email: "+email+" not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody EmailAndPasswordDTO emailAndPassword){
        return userService.deleteUser(emailAndPassword.getEmail(), emailAndPassword.getPassword());
    }

   /* @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(){

    }*/


}

