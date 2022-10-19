package com.eshop;

import com.eshop.dto.UserSignupDTO;
import com.eshop.model.*;
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
import java.io.File;
import java.io.FileInputStream;
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
                          RoleRepository roleRepository, OrderService orderService, BrandService brandService) {
        return args -> {

            File avatar1 = new File("src/main/resources/templates/image/ali.jpg");
            byte[] avatarBytes1 = new byte[(int)avatar1.length()];
            FileInputStream fileInputStream = new FileInputStream(avatar1);
            fileInputStream.read(avatarBytes1);
            UserSignupDTO user = new UserSignupDTO("Ali", "herawi",
                    "aliherawi7@gmail.com", LocalDate.of(1999, 3, 25),
                    "12345",avatarBytes1 , "Afghanistan");
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
                    1000d, "latest iphone", LocalDate.now(), "mdeuim",12L, 4d
            );
            File f2 = new File("src/main/resources/templates/image/7.png");
            FileInputStream fIn2 = new FileInputStream(f2);
            byte[] bytes2 = new byte[(int) f2.length()];
            fIn2.read(bytes2);
            Product p2 = new Product(
                    null, "apple TV", "yellow", bytes2,
                    "apple", "TV", 500d, "family tv",
                    LocalDate.now(), "mdeuim",15L, 3d
            );
            File f3 = new File("src/main/resources/templates/image/3.png");
            FileInputStream fIn3 = new FileInputStream(f3);
            byte[] bytes3 = new byte[(int) f3.length()];
            fIn3.read(bytes3);
            Product p3 = new Product(
                    null, "macbook pro", "white", bytes3,
                    "apple", "Laptop", 960d, "laptop pc",
                    LocalDate.now(), "mdeuim",15L, 5d
            );
            File f4 = new File("src/main/resources/templates/image/4.png");
            FileInputStream fIn4 = new FileInputStream(f4);
            byte[] bytes4 = new byte[(int) f4.length()];
            fIn4.read(bytes4);
            Product p4 = new Product(
                    null, "iphone 13", "red", bytes4,
                    "apple", "Mobile", 960d, "latest iphone",
                    LocalDate.now(), "mini",100L, 5d
            );
            File f5 = new File("src/main/resources/templates/image/5.png");
            FileInputStream fIn5 = new FileInputStream(f5);
            byte[] bytes5 = new byte[(int) f5.length()];
            fIn5.read(bytes5);
            Product p5 = new Product(
                    null, "MacBook 14 pro", "black", bytes5,
                    "apple", "Laptop pc", 1000d, "latest mac",
                    LocalDate.now(), "mini",100L, 4d
            );

            File f6 = new File("src/main/resources/templates/image/asus-gaming.png");
            FileInputStream fIn6 = new FileInputStream(f6);
            byte[] bytes6 = new byte[(int) f6.length()];
            fIn6.read(bytes6);
            Product p6 = new Product(
                    null, "asus-gaming", "black", bytes6,
                    "asus", "pc", 30d, "latest asus-gaming",
                    LocalDate.now(), "large",230L, 5d
            );
            File f7 = new File("src/main/resources/templates/image/2.png");
            FileInputStream fIn7 = new FileInputStream(f7);
            byte[] bytes7 = new byte[(int) f7.length()];
            fIn7.read(bytes7);
            Product p7 = new Product(
                    null, "Netflix TV", "black", bytes7,
                    "Netflix", "TV", 30d, "latest netflix",
                    LocalDate.now(), "large",230L, 4d
            );
            File f8 = new File("src/main/resources/templates/image/8.png");
            FileInputStream fIn8 = new FileInputStream(f8);
            byte[] bytes8 = new byte[(int) f8.length()];
            fIn8.read(bytes8);
            Product p8 = new Product(
                    null, "Apple watch 14 pro", "white", bytes8,
                    "apple", "watch", 300d, "latest apple watch",
                    LocalDate.now(), "medium",200L, 3d
            );
            File f9 = new File("src/main/resources/templates/image/9.png");
            FileInputStream fIn9 = new FileInputStream(f9);
            byte[] bytes9 = new byte[(int) f9.length()];
            fIn9.read(bytes9);
            Product p9 = new Product(
                    null, "iphone 13 mini", "blue", bytes9,
                    "apple", "mobile", 990d, "latest apple phone",
                    LocalDate.now(), "mini",200L, 5d
            );
            File f10 = new File("src/main/resources/templates/image/11.png");
            FileInputStream fIn10 = new FileInputStream(f10);
            byte[] bytes10 = new byte[(int) f10.length()];
            fIn10.read(bytes10);
            Product p10 = new Product(
                    null, "Samsung A71", "blue", bytes10,
                    "samsung", "mobile", 500d, "latest samsung phone",
                    LocalDate.now(), "large",200L, 4d
            );

            File f11 = new File("src/main/resources/templates/image/headphone.png");
            FileInputStream fIn11 = new FileInputStream(f11);
            byte[] bytes11 = new byte[(int) f11.length()];
            fIn11.read(bytes11);
            Product p11 = new Product(
                    null, "Modern Headphone", "blue", bytes11,
                    "jax", "headphone", 500d, "latest jax headphone",
                    LocalDate.now(), "large",200L, 4d
            );

            File f12 = new File("src/main/resources/templates/image/headphone600x.png");
            FileInputStream fIn12 = new FileInputStream(f12);
            byte[] bytes12 = new byte[(int) f12.length()];
            fIn12.read(bytes12);
            Product p12 = new Product(
                    null, "Headphone 600x", "red", bytes12,
                    "jax", "headphone", 500d, "latest jax headphone",
                    LocalDate.now(), "large",200L, 4d
            );
            File f13 = new File("src/main/resources/templates/image/airpods-3.png");
            FileInputStream fIn13 = new FileInputStream(f13);
            byte[] bytes13 = new byte[(int) f13.length()];
            fIn13.read(bytes13);
            Product p13 = new Product(
                    null, "airpods", "red", bytes13,
                    "apple", "headphone", 500d, "latest airpod",
                    LocalDate.now(), "large",200L, 4d
            );

            File f14 = new File("src/main/resources/templates/image/r9.png");
            FileInputStream fIn14 = new FileInputStream(f14);
            byte[] bytes14 = new byte[(int) f14.length()];
            fIn14.read(bytes14);
            Product p14 = new Product(
                    null, "r9", "red", bytes14,
                    "Xiaomi", "mobile", 500d, "latest Xiaomi headphone",
                    LocalDate.now(), "large",200L, 4d
            );
            File f15 = new File("src/main/resources/templates/image/H10_ID.png");
            FileInputStream fIn15 = new FileInputStream(f15);
            byte[] bytes15 = new byte[(int) f15.length()];
            fIn15.read(bytes15);
            Product p15 = new Product(
                    null, "H10", "red", bytes15,
                    "Xiaomi", "mobile", 500d, "latest Xiaomi ",
                    LocalDate.now(), "large",200L, 4d
            );

            File f16 = new File("src/main/resources/templates/image/c06.png");
            FileInputStream fIn16 = new FileInputStream(f16);
            byte[] bytes16 = new byte[(int) f16.length()];
            fIn16.read(bytes16);
            Product p16 = new Product(
                    null, "Laptop c06", "red", bytes16,
                    "HP", "laptop", 500d, "latest HP ",
                    LocalDate.now(), "large",200L, 4d
            );

            File f17 = new File("src/main/resources/templates/image/c9.png");
            FileInputStream fIn17 = new FileInputStream(f17);
            byte[] bytes17 = new byte[(int) f17.length()];
            fIn17.read(bytes17);
            Product p17 = new Product(
                    null, "Laptop c9", "red", bytes17,
                    "HP", "laptop", 500d, "latest HP ",
                    LocalDate.now(), "large",200L, 4d
            );

            File f18 = new File("src/main/resources/templates/image/laptop.png");
            FileInputStream fIn18 = new FileInputStream(f18);
            byte[] bytes18 = new byte[(int) f18.length()];
            fIn18.read(bytes18);
            Product p18 = new Product(
                    null, "ASUS studio Book", "red", bytes18,
                    "Asus", "laptop", 500d, "latest Asus ",
                    LocalDate.now(), "large",200L, 4d
            );
            File f19 = new File("src/main/resources/templates/image/laptop-acer-1.png");
            FileInputStream fIn19 = new FileInputStream(f19);
            byte[] bytes19 = new byte[(int) f19.length()];
            fIn19.read(bytes19);
            Product p19 = new Product(
                    null, "acer studio Book", "white", bytes19,
                    "acer", "laptop", 500d, "latest acer ",
                    LocalDate.now(), "large",200L, 4d
            );
            File f20 = new File("src/main/resources/templates/image/philips-takh402.png");
            FileInputStream fIn20 = new FileInputStream(f20);
            byte[] bytes20 = new byte[(int) f20.length()];
            fIn20.read(bytes20);
            Product p20 = new Product(
                    null, "philips-takh 402", "white", bytes20,
                    "philips", "headphone", 500d, "latest philips headphone ",
                    LocalDate.now(), "large",200L, 4d
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

            OrderApp order1 = new OrderApp(1L, 1L, 1L, "Afghanistan", 2, 10.0);
            OrderApp order2 = new OrderApp(2L, 1L, 1L, "Afghanistan", 3, 20.3);
            OrderApp order3 = new OrderApp(2L, 1L, 1L, "Afghanistan", 5, 100.5);
            orderService.addOrder(order1);
            orderService.addOrder(order2);
            orderService.addOrder(order3);

            // static brand
            File dellLogo = new File("src/main/resources/templates/image/brands/dell.png");
            FileInputStream logoFIS1 = new FileInputStream(dellLogo);
            byte[] dellByte = new byte[(int) dellLogo.length()];
            logoFIS1.read(dellByte);
            Brand b1 = new Brand(null, "dell",dellByte);

            File hpLogo = new File("src/main/resources/templates/image/brands/hp.png");
            FileInputStream logoFIS2 = new FileInputStream(hpLogo);
            byte[] hpByte = new byte[(int) hpLogo.length()];
            logoFIS2.read(hpByte);
            Brand b2 = new Brand(null, "hp",hpByte);

            File lgLogo = new File("src/main/resources/templates/image/brands/lg-electronics.png");
            FileInputStream logoFIS3 = new FileInputStream(lgLogo);
            byte[] lgByte = new byte[(int) hpLogo.length()];
            logoFIS3.read(lgByte);
            Brand b3 = new Brand(null, "lg",lgByte);

            File msLogo = new File("src/main/resources/templates/image/brands/microsoft.png");
            FileInputStream logoFIS4 = new FileInputStream(msLogo);
            byte[] msByte = new byte[(int) msLogo.length()];
            logoFIS4.read(msByte);
            Brand b4 = new Brand(null, "microsoft",msByte);

            File samsungLogo = new File("src/main/resources/templates/image/brands/samsung.png");
            FileInputStream logoFIS5 = new FileInputStream(samsungLogo);
            byte[] samsungByte = new byte[(int) samsungLogo.length()];
            logoFIS5.read(samsungByte);
            Brand b5 = new Brand(null, "samsung",samsungByte);

            File huaweiLogo = new File("src/main/resources/templates/image/brands/huawei.png");
            FileInputStream logoFIS6 = new FileInputStream(huaweiLogo);
            byte[] huaweiByte = new byte[(int) huaweiLogo.length()];
            logoFIS6.read(huaweiByte);
            Brand b6 = new Brand(null, "huawei",huaweiByte);

            File appleLogo = new File("src/main/resources/templates/image/brands/apple.png");
            FileInputStream logoFIS7 = new FileInputStream(appleLogo);
            byte[] appleByte = new byte[(int) appleLogo.length()];
            logoFIS7.read(appleByte);
            Brand b7 = new Brand(null, "apple",appleByte);

            brandService.addBrand(b1);
            brandService.addBrand(b2);
          //  brandService.addBrand(b3);
            brandService.addBrand(b4);
            brandService.addBrand(b5);
            brandService.addBrand(b6);
            brandService.addBrand(b7);
        };
    }


}
