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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
    CommandLineRunner run(ProductService p, UserService userService,
                          RoleRepository roleRepository, OrderService orderService,
                          BrandService brandService,
                          CommentRepository commentRepository,
                          CommentAgreeDisagreeRepository commentAgreeDisagreeRepository
                          ) {
        return args -> {

            UserSignupDTO user1 = new UserSignupDTO("Ali", "herawi",
                    "aliherawi7@gmail.com", LocalDate.of(1999, 3, 25),
                    "12345",null , "Afghanistan");

            UserSignupDTO user2 = new UserSignupDTO("Alexa", "Jhonson",
                    "Alexajhonson@gmail.com", LocalDate.of(2000, 3, 25),
                    "12345",null , "USA");


            UserSignupDTO user3 = new UserSignupDTO("Amanda", "Jepson",
                    "Amandajepson@gmail.com", LocalDate.of(2000, 3, 25),
                    "12345",null , "UK");


            Role role_user = new Role(1, "USER");
            Role role_admin = new Role(2, "ADMIN");
            roleRepository.save(role_admin);
            roleRepository.save(role_user);
            userService.addUser(user1);
            userService.addUser(user2);
            userService.addUser(user3);
            userService.addRoleToUser(user1.getEmail(), role_admin.getName());

            // static products










            Product p1 = new Product(
                    1L, "iPhone-14-Pro-Max-Purple",
                    "#ebebe7",
                    "apple", "mobile",
                    1000d, "latest iphone", LocalDate.now(), "medium",120L, 4d, 14D
            );

            Product p2 = new Product(
                    2L, "Samsung TV", "#171717",
                    "Samsung", "TV", 500d, "family tv",
                    LocalDate.now(), "medium",168L, 3d, 10D
            );

            Product p3 = new Product(
                    3L, "macbook pro", "#c0c1c4",
                    "apple", "pc", 960d, "laptop pc",
                    LocalDate.now(), "medium",650L, 5d, 10D
            );

            Product p4 = new Product(
                    4L, "iphone 14 pro space dark", "#c81e30",
                    "apple", "mobile", 1560d, "latest iphone",
                    LocalDate.now(), "pro",1500L, 5d, 6D
            );

            Product p5 = new Product(
                    5L, "MacBook 14 pro", "#c0c1c6",
                    "apple", "pc", 1000d, "latest mac",
                    LocalDate.now(), "mini",1000L, 4d, 5D
            );


            Product p6 = new Product(
                    6L, "asus-gaming", "#121212",
                    "asus", "pc", 30d, "latest asus-gaming",
                    LocalDate.now(), "large",623L, 5d, 10D
            );

            Product p7 = new Product(
                    7L, "Full HD LED TV", "#eaeaea",
                    "Sharp", "TV", 30d, "latest Sharp",
                    LocalDate.now(), "large",23L, 4d, 0D
            );

            Product p8 = new Product(
                    8L, "Apple watch 14 pro", "#dcdcdc",
                    "apple", "tools", 300d, "latest apple watch",
                    LocalDate.now(), "medium",730L, 3d, 10D
            );

            Product p9 = new Product(
                    9L, "modern stylish watch", "#f7f6f2",
                    "apple", "tools", 99d, "latest apple watch",
                    LocalDate.now(), "mini",200L, 5d, 8D
            );

            Product p10 = new Product(
                    10L, "Samsung A71", "#94e3d0",
                    "samsung", "mobile", 500d, "latest samsung phone",
                    LocalDate.now(), "large",1600L, 4d, 5D
            );

            Product p11 = new Product(
                    11L, "Modern Headphone", "#4f659f",
                    "jax", "tools", 500d, "latest jax headphone",
                    LocalDate.now(), "large",900L, 5d, 10D
            );

            Product p12 = new Product(
                    12L, "Headphone 600x", "#c23533",
                    "jax", "tools", 500d, "latest jax headphone",
                    LocalDate.now(), "large",1180L, 4d, 8D
            );

            Product p13 = new Product(
                    13L, "airpods", "#ebecf0",
                    "apple", "tools", 500d, "latest airpod",
                    LocalDate.now(), "large",1700L, 3d, 10D
            );

            Product p14 = new Product(
                    14L, "iphone 13 pro max silver", "#05c7c9",
                    "apple", "mobile", 500d, "iphone 13 pro max silver 128gb",
                    LocalDate.now(), "pro max",1400L, 5d, 15D
            );

            Product p15 = new Product(
                    15L, "H10", "#3cbdbb",
                    "Xiaomi", "mobile", 500d, "latest Xiaomi ",
                    LocalDate.now(), "large",1490L, 3d, 10D
            );

            Product p16 = new Product(
                    16L, "Laptop c06", "#525252",
                    "HP", "pc", 500d, "latest HP ",
                    LocalDate.now(), "large",120L, 5d, 5D
            );

            Product p17 = new Product(
                    17L, "ipad pro 14 ", "#edeeec",
                    "HP", "pc", 500d, "latest HP ",
                    LocalDate.now(), "large",100L, 4d, 10D
            );

            Product p18 = new Product(
                    18L, "ASUS studio Book", "#494753",
                    "Asus", "pc", 500d, "latest Asus ",
                    LocalDate.now(), "large",70L, 4d, 8D
            );

            Product p19 = new Product(
                    19L, "acer studio Book", "#aeafb3",
                    "acer", "pc", 500d, "latest acer ",
                    LocalDate.now(), "large",320L, 4d, 10D
            );

            Product p20 = new Product(
                    20L, "philips-takh 402", "#59c3fd",
                    "philips", "tools", 500d, "latest philips headphone ",
                    LocalDate.now(), "large",50L, 4d, 5D
            );

            //change the updated time
            p6.setUpdateInDepot(LocalDate.of(2022, 1, 23));
            p15.setUpdateInDepot(LocalDate.of(2022, 2, 23));
            p14.setUpdateInDepot(LocalDate.of(2022, 3, 23));
            p13.setUpdateInDepot(LocalDate.of(2022, 4, 23));
            p12.setUpdateInDepot(LocalDate.of(2022, 5, 23));
            p11.setUpdateInDepot(LocalDate.of(2022, 6, 23));
            p10.setUpdateInDepot(LocalDate.of(2022, 7, 23));
            p8.setUpdateInDepot(LocalDate.of(2022, 8, 23));
            p4.setUpdateInDepot(LocalDate.of(2022, 9, 23));
            p3.setUpdateInDepot(LocalDate.of(2022, 10, 23));
            p16.setUpdateInDepot(LocalDate.of(2021, 11, 23));
            p5.setUpdateInDepot(LocalDate.of(2021, 12, 23));

            p.addProduct(p1);
            p.addProduct(p2);
            p.addProduct(p3);
            p.addProduct(p4);
            p.addProduct(p5);
            p.addProduct(p6);
            p.addProduct(p7);
            p.addProduct(p8);
            p.addProduct(p9);
            p.addProduct(p10);
            p.addProduct(p11);
            p.addProduct(p12);
            p.addProduct(p13);
            p.addProduct(p14);
            p.addProduct(p15);
            p.addProduct(p16);
            p.addProduct(p17);
            p.addProduct(p18);
            p.addProduct(p19);
            p.addProduct(p20);

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

            Brand b1 = new Brand(null, "dell",null);
            Brand b2 = new Brand(null, "hp",null);
            Brand b4 = new Brand(null, "microsoft",null);
            Brand b5 = new Brand(null, "samsung",null);
            Brand b6 = new Brand(null, "huawei",null);
            Brand b7 = new Brand(null, "apple",null);

            brandService.addBrand(b1);
            brandService.addBrand(b2);
            brandService.addBrand(b4);
            brandService.addBrand(b5);
            brandService.addBrand(b6);
            brandService.addBrand(b7);


            // static comments

            p.getAllProducts().forEach(item ->{
                Comment c2 = new Comment(null, item.getProductId(), 2, LocalDateTime.now(),
                        "oh so expensive!", 5);
                Comment c3 = new Comment(null, item.getProductId(), 3, LocalDateTime.now(),
                        "is there any role for changing the arrived product?", 4);
                commentRepository.save(c2);
                commentRepository.save(c3);
            });

//            AgreeDisagree like1 =
//                    new AgreeDisagree(null, true, false, LocalDateTime.now(),2L, 2L);
//            commentAgreeDisagreeRepository.save(like1);

        };
    }


}
