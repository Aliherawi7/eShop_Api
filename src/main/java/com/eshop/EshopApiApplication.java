package com.eshop;

import com.eshop.dto.UserSignupDTO;
import com.eshop.model.*;
import com.eshop.repository.CommentAgreeDisagreeRepository;
import com.eshop.repository.CommentRepository;
import com.eshop.repository.RoleRepository;
import com.eshop.service.BrandService;
import com.eshop.service.OrderService;
import com.eshop.service.ProductService;
import com.eshop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


@SpringBootApplication
public class EshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopApiApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoderBean() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner run(ProductService productService, UserService userService,
                          RoleRepository roleRepository, OrderService orderService,
                          BrandService brandService,
                          CommentRepository commentRepository,
                          CommentAgreeDisagreeRepository commentAgreeDisagreeRepository
    ) {
        return args -> {

            UserSignupDTO user1 = new UserSignupDTO("Ali", "herawi",
                    "aliherawi7@gmail.com", LocalDate.of(1999, 3, 25),
                    "12345", null, "Afghanistan");

            UserSignupDTO user2 = new UserSignupDTO("Alexa", "Jhonson",
                    "Alexajhonson@gmail.com", LocalDate.of(2000, 3, 25),
                    "12345", null, "USA");

            UserSignupDTO user3 = new UserSignupDTO("Amanda", "Jepson",
                    "Amandajepson@gmail.com", LocalDate.of(2000, 3, 25),
                    "12345", null, "UK");

            Role role_user = new Role(1, "USER");
            Role role_admin = new Role(2, "ADMIN");
            roleRepository.save(role_admin);
            roleRepository.save(role_user);
            userService.addUser(user1);
            userService.addUser(user2);
            userService.addUser(user3);
            userService.addRoleToUser(user1.getEmail(), role_admin.getName());

            // static products
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Product> products = new ArrayList<>();
            try {
                URL jsonUrl = Thread.currentThread().getContextClassLoader().getResource("static\\products.json");
                Product[] array = objectMapper.readValue(jsonUrl, Product[].class);
                products.addAll(Arrays.asList(array));
                products.forEach(item -> {
                    System.out.println(item.getName());
                    productService.addProduct(item);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            // static orders

            OrderApp order1 = new OrderApp(1L, 9L, 1L, "Afghanistan", 10, 10.0);
            OrderApp order2 = new OrderApp(2L, 8L, 2L, "Afghanistan", 20, 20.3);
            OrderApp order3 = new OrderApp(3L, 2L, 1L, "Afghanistan", 40, 100.5);
            OrderApp order4 = new OrderApp(4L, 5L, 1L, "Afghanistan", 30, 10.0);
            OrderApp order5 = new OrderApp(5L, 6L, 2L, "Afghanistan", 80, 20.3);
            OrderApp order6 = new OrderApp(6L, 8L, 1L, "Afghanistan", 50, 100.5);
            order1.setOrderDate(LocalDate.of(2022, 7, 2));
            order2.setOrderDate(LocalDate.of(2022, 6, 23));
            order3.setOrderDate(LocalDate.of(2022, 5, 23));
            order4.setOrderDate(LocalDate.of(2022, 4, 23));
            order5.setOrderDate(LocalDate.of(2022, 3, 23));
            order6.setOrderDate(LocalDate.of(2022, 2, 23));
            orderService.addOrder(order1);
            orderService.addOrder(order2);
            orderService.addOrder(order3);
            orderService.addOrder(order4);
            orderService.addOrder(order5);
            orderService.addOrder(order6);
            // static brand

            Brand b2 = new Brand(2, "dell", null);
            Brand b3 = new Brand(3, "hp", null);
            Brand b5 = new Brand(5, "microsoft", null);
            Brand b6 = new Brand(6, "samsung", null);
            Brand b4 = new Brand(4, "huawei", null);
            Brand b1 = new Brand(1, "apple", null);
            brandService.addBrand(b1);
            brandService.addBrand(b2);
            brandService.addBrand(b3);
            brandService.addBrand(b4);
            brandService.addBrand(b5);
            brandService.addBrand(b6);


            // static comments

//            productService.getAllProducts().forEach(item ->{
//                Comment c2 = new Comment(null, item.getProductId(), 2, LocalDateTime.now(),
//                        "oh so expensive!", 5);
//                Comment c3 = new Comment(null, item.getProductId(), 3, LocalDateTime.now(),
//                        "is there any role for changing the arrived product?", 4);
//                commentRepository.save(c2);
//                commentRepository.save(c3);
//            });

            AgreeDisagree like1 =
                    new AgreeDisagree(null, true, false, LocalDateTime.now(), 2L, 2L);
            commentAgreeDisagreeRepository.save(like1);

        };
    }


}
