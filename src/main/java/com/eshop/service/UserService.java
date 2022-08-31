package com.eshop.service;

import com.eshop.model.Role;
import com.eshop.model.UserApp;
import com.eshop.repository.RoleRepository;
import com.eshop.repository.UserAppRepository;
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
    // duration of lock time for user that has been reached to max failed attempts
    private static final long LOCK_TIME_DURATION = 5 * 60 * 60 * 1000; // 5 hours


    private final UserAppRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserAppRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // implementing UserDetailService interface for authenticating user
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = userRepository.findByEmail(email);
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
    public ResponseEntity<?> addUser(UserApp user){
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
    public UserApp getUser(String email){
        return userRepository.findByEmail(email);
    }

    // add role to the user
    public ResponseEntity<?> addRoleToUser(String email, String roleName){
        UserApp user = userRepository.findByEmail(email);
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
        Collection<UserApp>  users = userRepository.findAll();
        if(users.size()>0){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no user is available!", HttpStatus.NOT_FOUND);
        }
    }

    // delete a user by email and password
    public ResponseEntity<?> deleteUser(String email, String password){
        email = email.trim().toLowerCase();
        UserApp user = userRepository.findByEmail(email);
        boolean matchPassword = bCryptPasswordEncoder.matches(user.getPassword(), password);
        // if user is not available
        if(user == null){
            return new ResponseEntity<>("user not found!", HttpStatus.NOT_FOUND);
        }
        if(matchPassword){
            userRepository.delete(user);
            return new ResponseEntity<>("user removed successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("incorrect password!",HttpStatus.FORBIDDEN);
        }
    }

    // check if account is locked
    public boolean isAccountLocked(UserApp user)throws UsernameNotFoundException{
        return user.isAccountLocked();
    }
    // updates the number of failed attempts of a user. it also called each time the user fails to login
    public void increaseFailedAttempts(UserApp user){
        int newFailedAttempts = user.getFailedAttempt() + 1;
        updateFailedAttempts(newFailedAttempts, user.getEmail());
    }
    // sets number of failed attempts to zero. this method will called when user has logged in successfully.
    public void resetFailedAttempts(String email){
        updateFailedAttempts(0, email);
    }
    public void updateFailedAttempts(int attempt, String email){
        UserApp user = userRepository.findByEmail(email);
        user.setFailedAttempt(attempt);
        userRepository.save(user);
    }
    // locks the user's account if the number of failed attempts reach the maximum allowed times.
    public void lock(UserApp user){
        user.setAccountLocked(true);
        user.setLockTime(new Date(System.currentTimeMillis()+LOCK_TIME_DURATION));
        userRepository.save(user);
    }
    // unlocks the user's account when lock duration expires, allowing the user to login as usual.
    public boolean unlockWhenTimeExpired(UserApp user){
        long lockTimeInMillis = user.getLockTime().getTime();
        if(lockTimeInMillis < System.currentTimeMillis()){
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
