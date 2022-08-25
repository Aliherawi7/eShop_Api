package com.eshop.service;

import com.eshop.model.Role;
import com.eshop.model.User;
import com.eshop.repository.RoleRepository;
import com.eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/*
 * @Author: Aliherawi
 * @Github: Aliherawi7
 * @linkedin: ali-herawi
 */
@Service
public class UserService implements UserDetailsService {
    // maximum number of failed login attempts allowed
    public static final int MAX_FAILED_ATTEMPTS = 5;
    // duration of failed login attempts allowed
    private static final long LOCK_TIME_DURATION = 12 * 60 * 60 * 1000; // 12 hours


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // implementing UserDetailService interface for authenticating user
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    //save user on database
    public ResponseEntity<?> addUser(User user){
        boolean exist = userRepository.existsByEmail(user.getEmail().toLowerCase().trim());
        if(exist){
            return new ResponseEntity<String>("User already exist", HttpStatus.BAD_REQUEST);
        }else{
            user.setEmail(user.getEmail().trim().toLowerCase());
            user.setName(user.getName().trim().toLowerCase());
            user.addRole(roleRepository.findByName("USER"));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<>("User save successfully!", HttpStatus.CREATED);
        }
    }

    // find user by email
    public User getUser(String email){
        return userRepository.findByEmail(email);
        /*if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("user with email: "+email+" not found!", HttpStatus.NOT_FOUND);
        }*/
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

    // check if account is locked
    public boolean isAccountLocked(User user)throws UsernameNotFoundException{
        return user.isAccountLocked();
    }
    // updates the number of failed attempts of a user. it also called each time the user fails to login
    public void increaseFailedAttempts(User user){
        int newFailedAttempts = user.getFailedAttempt() + 1;
        updateFailedAttempts(newFailedAttempts, user.getEmail());
    }
    // sets number of failed attempts to zero. this method will called when user has logged in successfully.
    public void resetFailedAttempts(String email){
        updateFailedAttempts(0, email);
    }
    public void updateFailedAttempts(int attempt, String email){
        User user = userRepository.findByEmail(email);
        user.setFailedAttempt(attempt);
        userRepository.save(user);
    }
    // locks the user's account if the number of failed attempts reach the maximum allowed times.
    public void lock(User user){
        user.setAccountLocked(true);
        user.setLockTime(new Date());
        userRepository.save(user);
    }
    // unlocks the user's account when lock duration expires, allowing the user to login as usual.
    public boolean unlockWhenTimeExpired(User user){
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        if(lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis){
            user.setAccountLocked(false);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            userRepository.save(user);
            return true;
        }
        return false;
    }






    //delete a logged in user
    /*
    public ResponseEntity<?> deleteLoggedInUser(String password){

    }*/


}
