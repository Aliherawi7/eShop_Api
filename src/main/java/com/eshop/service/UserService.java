package com.eshop.service;

import com.eshop.model.Role;
import com.eshop.model.User;
import com.eshop.repository.RoleRepository;
import com.eshop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    //save user on database
    public ResponseEntity<?> addUser(User user){
        boolean exist = userRepository.existsByEmail(user.getEmail());
        if(exist){
            return new ResponseEntity<String>("User already exist", HttpStatus.BAD_REQUEST);
        }else{
            userRepository.save(user);
            return new ResponseEntity<>("User save successfully!", HttpStatus.CREATED);
        }
    }

    // find user by email
    public ResponseEntity<?> getUser(String email){
        User user = userRepository.findByEmail(email);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("user with email: "+email+" not found!", HttpStatus.NOT_FOUND);
        }
    }
    // add role to the user
    public ResponseEntity<?> addRoleToUser(String email, String roleName){
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        if(user != null && role != null){
            user.addRole(role);
            userRepository.save(user);
            return new ResponseEntity<>("role: "+roleName+ " added successfully to the user", HttpStatus.OK);
        }else if(user == null && role ==null){
            return new ResponseEntity<>(
                    "user with email: "+email+"" +
                    " and role with name: "+
                    roleName+" not found!", HttpStatus.NOT_FOUND);
        }else if (user == null){
            return new ResponseEntity<>("user with email: "+email+"", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("role with name: "+ roleName+" not found!", HttpStatus.NOT_FOUND);
        }


    }



}
