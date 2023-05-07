package com.eshop;

import com.eshop.dto.UserRegistrationRequest;
import com.eshop.model.*;
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

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.time.LocalDate;
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
                          BrandService brandService

    ) {
        return args -> {

            UserRegistrationRequest user1 = new UserRegistrationRequest("Ali", "herawi",
                    "aliherawi7@gmail.com", LocalDate.of(1999, 3, 25).toString(),
                    "12345", null, "Afghanistan");

            UserRegistrationRequest user2 = new UserRegistrationRequest("Alexa", "Jhonson",
                    "Alexajhonson@gmail.com", LocalDate.of(2000, 3, 25).toString(),
                    "12345", null, "USA");

            UserRegistrationRequest user3 = new UserRegistrationRequest("Amanda", "Jepson",
                    "Amandajepson@gmail.com", LocalDate.of(2000, 3, 25).toString(),
                    "12345", null, "UK");

            Role role_user = new Role(1, "USER");
            Role role_admin = new Role(2, "ADMIN");
            roleRepository.save(role_admin);
            roleRepository.save(role_user);
            userService.addUserForTest(user1);
            userService.addUserForTest(user2);
            userService.addUserForTest(user3);
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
            OrderApp order7 = new OrderApp(7L, 12L, 1L, "Afghanistan", 80, 100.5);
            OrderApp order8 = new OrderApp(8L, 18L, 1L, "Afghanistan", 60, 100.5);
            OrderApp order9 = new OrderApp(9L, 20L, 1L, "Afghanistan", 40, 100.5);
            OrderApp order10 = new OrderApp(10L, 11L, 1L, "Afghanistan", 30, 100.5);
            OrderApp order11 = new OrderApp(11L, 8L, 1L, "Afghanistan", 20, 100.5);
            OrderApp order12 = new OrderApp(12L, 8L, 1L, "Afghanistan", 10, 100.5);
            OrderApp order13 = new OrderApp(13L, 8L, 1L, "Afghanistan", 10, 100.5);
            OrderApp order14 = new OrderApp(14L, 8L, 1L, "Afghanistan", 10, 100.5);
            order1.setOrderDate(LocalDate.of(2023, 5, 2));
            order2.setOrderDate(LocalDate.of(2023, 4, 3));
            order3.setOrderDate(LocalDate.of(2023, 3, 23));
            order4.setOrderDate(LocalDate.of(2023, 2, 21));
            order5.setOrderDate(LocalDate.of(2023, 1, 8));
            order6.setOrderDate(LocalDate.of(2022, 12, 9));
            order7.setOrderDate(LocalDate.of(2022, 11, 17));
            order8.setOrderDate(LocalDate.of(2022, 10, 12));
            order9.setOrderDate(LocalDate.of(2022, 9, 20));
            order10.setOrderDate(LocalDate.of(2022, 8, 3));
            order11.setOrderDate(LocalDate.of(2022, 7, 2));
            order12.setOrderDate(LocalDate.of(2022, 6, 23));
            order13.setOrderDate(LocalDate.of(2022, 10, 23));
            order14.setOrderDate(LocalDate.of(2022, 11, 23));
            orderService.addOrder(order1);
            orderService.addOrder(order2);
            orderService.addOrder(order3);
            orderService.addOrder(order4);
            orderService.addOrder(order5);
            orderService.addOrder(order6);
            orderService.addOrder(order7);
            orderService.addOrder(order8);
            orderService.addOrder(order9);
            orderService.addOrder(order10);
            orderService.addOrder(order11);
            orderService.addOrder(order12);
            orderService.addOrder(order13);
            orderService.addOrder(order14);
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

        };
    }


}
