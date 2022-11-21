package com.eshop.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eshop.dto.SignupInformationDTO;
import com.eshop.dto.UserInformationDTO;
import com.eshop.dto.UserSignupDTO;
import com.eshop.model.OrderApp;
import com.eshop.model.Role;
import com.eshop.model.UserApp;
import com.eshop.repository.OrderRepository;
import com.eshop.repository.RoleRepository;
import com.eshop.repository.UserAppRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    public UserService(UserAppRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    // implementing UserDetailService interface for authenticating user
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    //save user on database
    public ResponseEntity<?> addUser(UserSignupDTO user) {
        String email;
        UserApp exist = userRepository.findByEmail(user.getEmail().toLowerCase().trim());
        if (exist != null) {
            Map<String, String> body = new HashMap<>();
            body.put("error_message", "email has taken. User already exist!");
            body.put("status_code", HttpStatus.NON_AUTHORITATIVE_INFORMATION.name());
            return new ResponseEntity<>(body, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } else {
            email = user.getEmail().toLowerCase().trim();
            UserApp userApp = new UserApp();
            userApp.setEmail(email);
            userApp.setUserName(email.substring(0, email.indexOf("@")));
            userApp.setName(user.getName());
            userApp.setLastName(user.getLastName());
            userApp.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userApp.setImgUrl(user.getImage());
            userApp.setLocation(user.getLocation());
            userApp.addRole(roleRepository.findByName("USER"));
            userRepository.save(userApp);


            UserInformationDTO userInfo = new UserInformationDTO(userApp.getId(), userApp.getName(),userApp.getLastName(),
                    userApp.getImage(), userApp.getEmail(),
                    userApp.getRoles().stream().map(Role::getName).collect(Collectors.toList()),
                    userApp.getLocation() ,0, 0, userApp.isEnabled());
            Algorithm algorithm = Algorithm.HMAC256("herawi".getBytes());
            String accessToken = JWT.create()
                    .withSubject(userApp.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000*60*60*24*10)))
                    .withClaim("roles", userApp.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);

            return new ResponseEntity<>(new SignupInformationDTO(accessToken, userInfo), HttpStatus.CREATED);
        }
    }

    // find user by email
    public UserApp getUser(String email) {
        return userRepository.findByEmail(email);
    }

    // find user by id
    public UserApp getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    // add role to the user
    public ResponseEntity<?> addRoleToUser(String email, String roleName) {
        UserApp user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        if (user != null && role != null) {
            user.addRole(role);
            userRepository.save(user);
            return new ResponseEntity<>("role: " + roleName + " added successfully to the user", HttpStatus.OK);
        } else if (user == null && role == null) {
            return new ResponseEntity<>(
                    "user with email: " + email + "" +
                            " and role with name: " +
                            roleName + " not found!", HttpStatus.NO_CONTENT);
        } else if (user == null) {
            return new ResponseEntity<>("user with email: " + email + "", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("role with name: " + roleName + " not found!", HttpStatus.NO_CONTENT);
        }
    }

    // get all users from the database
    public ResponseEntity<?> getAllUsers() {
        Collection<UserApp> users = userRepository.findAll();
        // user common information
        Collection<UserInformationDTO> usersInfo = users.stream()
                .map(userApp -> {
                    int totalOrder =0;
                    double totalSpending = 0;
                    if(orderRepository.findAllByUserId(userApp.getId()) != null){
                        totalOrder = orderRepository.findAllByUserId(userApp.getId()).size();
                        totalSpending = orderRepository.findAllByUserId(userApp.getId()).stream().mapToDouble(OrderApp::getAmount).sum();
                    }
                    return new UserInformationDTO(userApp.getId(), userApp.getName(), userApp.getLastName(),
                            userApp.getImage(), userApp.getEmail(),
                            userApp.getRoles().stream().map((Role::getName)).collect(Collectors.toList()),
                            userApp.getLocation(), totalOrder, totalSpending, userApp.isEnabled());
                })
                .collect(Collectors.toList());
        if (users.size() > 0) {
            return new ResponseEntity<>(usersInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no user is available!", HttpStatus.NO_CONTENT);
        }
    }

    // delete a user by email and password
    public ResponseEntity<?> deleteUser(String email, String password) {
        email = email.trim().toLowerCase();
        UserApp user = userRepository.findByEmail(email);
        // if user is not available
        if (user == null) {
            return new ResponseEntity<>("user not found!", HttpStatus.NOT_FOUND);
        }
        boolean matchPassword = bCryptPasswordEncoder.matches(user.getPassword(), password);

        if (matchPassword) {
            userRepository.delete(user);
            return new ResponseEntity<>("user removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("incorrect password!", HttpStatus.FORBIDDEN);
        }
    }

    // check if account is locked
    public boolean isAccountLocked(UserApp user) throws UsernameNotFoundException {
        return user.isAccountLocked();
    }

    // updates the number of failed attempts of a user. it also called each time the user fails to login
    public void increaseFailedAttempts(UserApp user) {
        int newFailedAttempts = user.getFailedAttempt() + 1;
        updateFailedAttempts(newFailedAttempts, user.getEmail());
    }

    // sets number of failed attempts to zero. this method will called when user has logged in successfully.
    public void resetFailedAttempts(String email) {
        updateFailedAttempts(0, email);
    }

    public void updateFailedAttempts(int attempt, String email) {
        UserApp user = userRepository.findByEmail(email);
        user.setFailedAttempt(attempt);
        userRepository.save(user);
    }

    // locks the user's account if the number of failed attempts reach the maximum allowed times.
    public void lock(UserApp user) {
        user.setAccountLocked(true);
        user.setLockTime(new Date(System.currentTimeMillis() + LOCK_TIME_DURATION));
        userRepository.save(user);
    }

    // unlocks the user's account when lock duration expires, allowing the user to login as usual.
    public boolean unlockWhenTimeExpired(UserApp user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        if (lockTimeInMillis < System.currentTimeMillis()) {
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
