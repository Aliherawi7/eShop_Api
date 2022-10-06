package com.eshop;

import com.eshop.dto.UserSignupDTO;
import com.eshop.model.OrderApp;
import com.eshop.model.Product;
import com.eshop.model.Role;
import com.eshop.model.UserApp;
import com.eshop.repository.RoleRepository;
import com.eshop.service.OrderService;
import com.eshop.service.ProductService;
import com.eshop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;


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
                          RoleRepository roleRepository, OrderService orderService) {
        return args -> {
            UserSignupDTO user = new UserSignupDTO("Ali", "herawi",
                    "aliherawi7@gmail.com", LocalDate.of(1999, 3, 25),
                    "12345", new byte[5]);

            Role role_user = new Role(1, "USER");
            Role role_admin = new Role(2, "ADMIN");
            roleRepository.save(role_admin);
            roleRepository.save(role_user);
            userService.addUser(user);
            userService.addRoleToUser(user.getEmail(), role_admin.getName());

            // static products
            File f1 = new File("src/main/resources/templates/image/1.png");
            FileInputStream fIn1 = new FileInputStream(f1);
            byte[] bytes1 = new byte[(int) f1.length()];
            fIn1.read(bytes1);
            Product p1 = new Product(
                    null, "iphone 13 pro",
                    "red", bytes1,
                    "apple", "mobile",
                    1000d, "latest iphone", LocalDate.now(), "mdeuim",12l
            );
            File f2 = new File("src/main/resources/templates/image/7.png");
            FileInputStream fIn2 = new FileInputStream(f2);
            byte[] bytes2 = new byte[(int) f2.length()];
            fIn2.read(bytes2);
            Product p2 = new Product(
                    null, "apple TV", "yellow", bytes2,
                    "apple", "TV", 500d, "family tv",
                    LocalDate.now(), "mdeuim",15l
            );
            File f3 = new File("src/main/resources/templates/image/3.png");
            FileInputStream fIn3 = new FileInputStream(f3);
            byte[] bytes3 = new byte[(int) f3.length()];
            fIn3.read(bytes3);
            Product p3 = new Product(
                    null, "macbook pro", "white", bytes3,
                    "apple", "Laptop", 960d, "laptop pc",
                    LocalDate.now(), "mdeuim",15l
            );
            File f4 = new File("src/main/resources/templates/image/4.png");
            FileInputStream fIn4 = new FileInputStream(f4);
            byte[] bytes4 = new byte[(int) f4.length()];
            fIn4.read(bytes4);
            Product p4 = new Product(
                    null, "iphone 13", "red", bytes4,
                    "apple", "Mobile", 960d, "latest iphone",
                    LocalDate.now(), "mini",100l
            );
            File f5 = new File("src/main/resources/templates/image/5.png");
            FileInputStream fIn5 = new FileInputStream(f5);
            byte[] bytes5 = new byte[(int) f5.length()];
            fIn5.read(bytes5);
            Product p5 = new Product(
                    null, "MacBook 14 pro", "black", bytes5,
                    "apple", "Laptop pc", 1000d, "latest mac",
                    LocalDate.now(), "mini",100l
            );
            File f6 = new File("src/main/resources/templates/image/6.png");
            FileInputStream fIn6 = new FileInputStream(f6);
            byte[] bytes6 = new byte[(int) f6.length()];
            fIn6.read(bytes6);
            Product p6 = new Product(
                    null, "Keyboard max", "black", bytes6,
                    "apple", "Laptop pc", 30d, "latest max",
                    LocalDate.now(), "large",230l
            );
            File f7 = new File("src/main/resources/templates/image/2.png");
            FileInputStream fIn7 = new FileInputStream(f7);
            byte[] bytes7 = new byte[(int) f7.length()];
            fIn7.read(bytes7);
            Product p7 = new Product(
                    null, "Netflix TV", "black", bytes7,
                    "Netflix", "TV", 30d, "latest netflix",
                    LocalDate.now(), "large",230l
            );
            File f8 = new File("src/main/resources/templates/image/8.png");
            FileInputStream fIn8 = new FileInputStream(f8);
            byte[] bytes8 = new byte[(int) f8.length()];
            fIn8.read(bytes8);
            Product p8 = new Product(
                    null, "Apple watch 14 pro", "white", bytes8,
                    "apple", "watch", 300d, "latest apple watch",
                    LocalDate.now(), "medium",200l
            );
            File f9 = new File("src/main/resources/templates/image/9.png");
            FileInputStream fIn9 = new FileInputStream(f9);
            byte[] bytes9 = new byte[(int) f9.length()];
            fIn9.read(bytes9);
            Product p9 = new Product(
                    null, "iphone 13 mini", "blue", bytes9,
                    "apple", "mobile", 990d, "latest apple phone",
                    LocalDate.now(), "mini",200l
            );
            File f10 = new File("src/main/resources/templates/image/11.png");
            FileInputStream fIn10 = new FileInputStream(f10);
            byte[] bytes10 = new byte[(int) f10.length()];
            fIn10.read(bytes10);
            Product p10 = new Product(
                    null, "Samsung A71", "blue", bytes10,
                    "samsung", "mobile", 500d, "latest samsung phone",
                    LocalDate.now(), "large",200l
            );
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
            // static orders

            OrderApp order1 = new OrderApp(1l, 1l, 1l, "Afghanistan", 2, 10.0);
            OrderApp order2 = new OrderApp(2l, 1l, 1l, "Afghanistan", 3, 20.3);
            OrderApp order3 = new OrderApp(2l, 1l, 1l, "Afghanistan", 5, 100.5);
            orderService.addOrder(order1);
            orderService.addOrder(order2);
            orderService.addOrder(order3);

        };
    }


}
