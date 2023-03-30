package com.eshop.resources;

import com.eshop.dto.AddRoleToUserDTO;
import com.eshop.dto.EmailAndPasswordDTO;
import com.eshop.dto.UserInformationDTO;
import com.eshop.dto.UserSignupDTO;
import com.eshop.model.UserApp;
import com.eshop.security.TestUserWithJWT;
import com.eshop.service.IPFinderService;
import com.eshop.service.UserService;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
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
    public ResponseEntity<?> addUser(@RequestParam("image") MultipartFile file, @RequestParam Map<MysqlxDatatypes.Scalar.String, String> params, HttpServletRequest request) throws IOException {
        UserSignupDTO userInfo = new UserSignupDTO();
        userInfo.setName(params.get("name"));
        userInfo.setLastName(params.get("lastName"));
        userInfo.setEmail(params.get("email"));
        userInfo.setPassword(params.get("password"));
        //check if there no file then set the default avatar to the user
        if(file.isEmpty()){
            File avatar = new File("src/main/resources/templates/image/ali.jpg");
            byte[] avatarBytes = new byte[(int)avatar.length()];
            FileInputStream fileInputStream = new FileInputStream(avatar);
            fileInputStream.read(avatarBytes);
            userInfo.setImage(avatarBytes);
        }else{
            userInfo.setImage(file.getBytes());
        }

        userInfo.setDob(LocalDate.parse(params.get("dob")));
        String ipAddress = IPFinderService.getClientIP(request).equals("127.0.0.1") ? "168.211.152.24" : IPFinderService.getClientIP(request);
        String countryName = IPFinderService.getCountryName(ipAddress);
        userInfo.setLocation(countryName);
        return userService.addUser(userInfo);
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
        userDTO.setDob(user.getDob());
        userDTO.setLocation(user.getLocation());
        userDTO.setImage(user.getImage());
        if (user != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with email: " + jwtEmail + " not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(
            @RequestParam("image") MultipartFile file, @RequestParam Map<String, String> params, HttpServletRequest request
    ) throws Exception {
        String email = TestUserWithJWT.getUserEmailByJWT(request);
        UserInformationDTO user = userService.updateUser(email,file, params);
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
   public ResponseEntity<?> addRoleToUser(AddRoleToUserDTO emailAndPassword){
        return userService
                .addRoleToUser(emailAndPassword.getUserEmail(), emailAndPassword.getRoleName());
   }



}

