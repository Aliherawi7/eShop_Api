package com.eshop.service;

import com.eshop.model.Role;
import com.eshop.model.User;
import com.eshop.repository.RoleRepository;
import com.eshop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/*
 * @Author: Aliherawi
 * @Github: Aliherawi7
 * @linkedin: ali-herawi
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    // get all users from the database
    public ResponseEntity<?> getAllUsers(){
        Collection<User>  users = userRepository.findAll();
        if(users.size()>0){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no user is available!", HttpStatus.NOT_FOUND);
        }
    }

    // delete a user by email and password
    public ResponseEntity<?> deleteUser(String email, String password){
        email = email.trim().toLowerCase();
        User user = userRepository.findByEmail(email);
        boolean matchPassword = bCryptPasswordEncoder.matches(user.getPassword(), password);
        // if user is not available
        if(user == null){
            return new ResponseEntity<>("user not found!", HttpStatus.NOT_FOUND);
        }
        if(matchPassword){
            userRepository.delete(user);
            return new ResponseEntity<>("user removed successfully", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("incorrect password!",HttpStatus.NOT_FOUND);
        }
    }

    //delete a logged in user
/*    public ResponseEntity<?> deleteLoggedInUser(String password){

    }*/


}
