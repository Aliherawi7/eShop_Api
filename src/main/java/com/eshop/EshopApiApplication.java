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
import org.h2.api.H2Type;
import org.hibernate.dialect.H2Dialect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
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


    /*@Bean
    CommandLineRunner run(ProductService p, UserService userService,
                          RoleRepository roleRepository, OrderService orderService) {
        return args -> {
            UserSignupDTO user = new UserSignupDTO("Ali", "herawi", "Aliherawi7@gmail.com", LocalDate.of(1999, 3, 25), "1234", "image url");

            Role role_user = new Role(1, "USER");
            Role role_admin = new Role(2, "ADMIN");
            roleRepository.save(role_admin);
            roleRepository.save(role_user);
            userService.addUser(user);
            userService.addRoleToUser(user.getEmail(), role_admin.getName());

            Product p1 = new Product(
                    null, "a10s", "red", "img url", "samsung", "mobile", 99.5, "my mob", LocalDate.now(), "mdeuim"
            );
            p.addProduct(p1);

            //orders

        };
    }*/


}
