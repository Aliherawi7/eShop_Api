package com.eshop;

import com.eshop.model.ProductSidesImages;
import com.eshop.dto.UserSignupDTO;
import com.eshop.model.*;
import com.eshop.repository.CommentAgreeDisagreeRepository;
import com.eshop.repository.CommentRepository;
import com.eshop.repository.ProductImageRepository;
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
                          CommentAgreeDisagreeRepository commentAgreeDisagreeRepository,
                          ProductImageRepository productImageRepository
                          ) {
        return args -> {

            File avatar1 = new File("src/main/resources/templates/image/users/user1.jpg");
            byte[] avatarBytes1 = new byte[(int)avatar1.length()];
            FileInputStream fileInputStream1 = new FileInputStream(avatar1);
            fileInputStream1.read(avatarBytes1);
            UserSignupDTO user1 = new UserSignupDTO("Ali", "herawi",
                    "aliherawi7@gmail.com", LocalDate.of(1999, 3, 25),
                    "12345",avatarBytes1 , "Afghanistan");
            File avatar2 = new File("src/main/resources/templates/image/users/user2.jpg");
            byte[] avatarBytes2 = new byte[(int)avatar2.length()];
            FileInputStream fileInputStream2 = new FileInputStream(avatar2);
            fileInputStream2.read(avatarBytes2);
            UserSignupDTO user2 = new UserSignupDTO("Alexa", "Jhonson",
                    "Alexajhonson@gmail.com", LocalDate.of(2000, 3, 25),
                    "12345",avatarBytes2 , "USA");

            File avatar3 = new File("src/main/resources/templates/image/users/user3.jpg");
            byte[] avatarBytes3 = new byte[(int)avatar3.length()];
            FileInputStream fileInputStream3 = new FileInputStream(avatar3);
            fileInputStream3.read(avatarBytes3);
            UserSignupDTO user3 = new UserSignupDTO("Amanda", "Jepson",
                    "Amandajepson@gmail.com", LocalDate.of(2000, 3, 25),
                    "12345",avatarBytes3 , "UK");


            Role role_user = new Role(1, "USER");
            Role role_admin = new Role(2, "ADMIN");
            roleRepository.save(role_admin);
            roleRepository.save(role_user);
            userService.addUser(user1);
            userService.addUser(user2);
            userService.addUser(user3);
            userService.addRoleToUser(user1.getEmail(), role_admin.getName());

            // static products



            File f1side1 = new File("src/main/resources/templates/image/iPhone-14-Pro-Max-Purple-side1.png");
            FileInputStream fIn1side1 = new FileInputStream(f1side1);
            byte[] bytes1side1 = new byte[(int) f1side1.length()];
            fIn1side1.read(bytes1side1);

            File f1side2 = new File("src/main/resources/templates/image/iPhone-14-Pro-Max-Purple-side1.png");
            FileInputStream fIn1side2 = new FileInputStream(f1side2);
            byte[] bytes1side2 = new byte[(int) f1side2.length()];
            fIn1side2.read(bytes1side2);

            File f1side3 = new File("src/main/resources/templates/image/iPhone-14-Pro-Max-Purple-side3.png");
            FileInputStream fIn1side3 = new FileInputStream(f1side3);
            byte[] bytes1side3 = new byte[(int) f1side3.length()];
            fIn1side3.read(bytes1side3);

            ProductSidesImages primg1 = new ProductSidesImages();
            primg1.setSide1(bytes1side1);
            primg1.setSide2(bytes1side2);
            primg1.setSide3(bytes1side3);
            primg1.setProductId(1);
            productImageRepository.save(primg1);
            Product p1 = new Product(
                    1L, "iPhone-14-Pro-Max-Purple",
                    "#ebebe7",
                    "apple", "mobile",
                    1000d, "latest iphone", LocalDate.now(), "medium",120L, 4d, 14D
            );
            File f2 = new File("src/main/resources/templates/image/7.png");
            FileInputStream fIn2 = new FileInputStream(f2);
            byte[] bytes2 = new byte[(int) f2.length()];
            fIn2.read(bytes2);
            ProductSidesImages primg2 = new ProductSidesImages();
            primg2.setSide1(bytes2);
            primg2.setProductId(2);
            productImageRepository.save(primg2);
            Product p2 = new Product(
                    2L, "apple TV", "#171717",
                    "apple", "TV", 500d, "family tv",
                    LocalDate.now(), "medium",168L, 3d, 10D
            );
            File f3 = new File("src/main/resources/templates/image/3.png");
            FileInputStream fIn3 = new FileInputStream(f3);
            byte[] bytes3 = new byte[(int) f3.length()];
            fIn3.read(bytes3);
            ProductSidesImages primg3 = new ProductSidesImages();
            primg3.setSide1(bytes3);
            primg3.setProductId(3);
            productImageRepository.save(primg3);
            Product p3 = new Product(
                    3L, "macbook pro", "#c0c1c4",
                    "apple", "pc", 960d, "laptop pc",
                    LocalDate.now(), "medium",650L, 5d, 10D
            );
            File f4 = new File("src/main/resources/templates/image/4.png");
            FileInputStream fIn4 = new FileInputStream(f4);
            byte[] bytes4 = new byte[(int) f4.length()];
            fIn4.read(bytes4);
            ProductSidesImages primg4 = new ProductSidesImages();
            primg4.setSide1(bytes4);
            primg4.setProductId(4);
            productImageRepository.save(primg4);
            Product p4 = new Product(
                    4L, "iphone 13", "#c81e30",
                    "apple", "Mobile", 960d, "latest iphone",
                    LocalDate.now(), "mini",1500L, 5d, 6D
            );
            File f5 = new File("src/main/resources/templates/image/5.png");
            FileInputStream fIn5 = new FileInputStream(f5);
            byte[] bytes5 = new byte[(int) f5.length()];
            fIn5.read(bytes5);
            ProductSidesImages primg5 = new ProductSidesImages();
            primg5.setSide1(bytes5);
            primg5.setProductId(5);
            productImageRepository.save(primg5);
            Product p5 = new Product(
                    5L, "MacBook 14 pro", "#c0c1c6",
                    "apple", "pc", 1000d, "latest mac",
                    LocalDate.now(), "mini",1000L, 4d, 5D
            );

            File f6 = new File("src/main/resources/templates/image/asus-gaming.png");
            FileInputStream fIn6 = new FileInputStream(f6);
            byte[] bytes6 = new byte[(int) f6.length()];
            fIn6.read(bytes6);
            ProductSidesImages primg6 = new ProductSidesImages();
            primg6.setSide1(bytes6);
            primg6.setProductId(6);
            productImageRepository.save(primg6);
            Product p6 = new Product(
                    6L, "asus-gaming", "#121212",
                    "asus", "pc", 30d, "latest asus-gaming",
                    LocalDate.now(), "large",623L, 5d, 10D
            );
            File f7 = new File("src/main/resources/templates/image/2.png");
            FileInputStream fIn7 = new FileInputStream(f7);
            byte[] bytes7 = new byte[(int) f7.length()];
            fIn7.read(bytes7);
            ProductSidesImages primg7 = new ProductSidesImages();
            primg7.setSide1(bytes7);
            primg7.setProductId(7);
            productImageRepository.save(primg7);
            Product p7 = new Product(
                    7L, "Full HD LED TV", "#eaeaea",
                    "Sharp", "TV", 30d, "latest Sharp",
                    LocalDate.now(), "large",23L, 4d, 0D
            );

            File f8side1 = new File("src/main/resources/templates/image/watch1_side1.png");
            FileInputStream fIn8side1 = new FileInputStream(f8side1);
            byte[] bytes8side1 = new byte[(int) f8side1.length()];
            fIn8side1.read(bytes8side1);

            File f8side2 = new File("src/main/resources/templates/image/watch1_side2.png");
            FileInputStream fIn8side2 = new FileInputStream(f8side2);
            byte[] bytes8side2 = new byte[(int) f8side2.length()];
            fIn8side2.read(bytes8side2);

            File f8side3 = new File("src/main/resources/templates/image/watch1_side3.png");
            FileInputStream fIn8side3 = new FileInputStream(f8side3);
            byte[] bytes8side3 = new byte[(int) f8side3.length()];
            fIn8side3.read(bytes8side3);

            ProductSidesImages primg8 = new ProductSidesImages();
            primg8.setSide1(bytes8side1);
            primg8.setSide2(bytes8side2);
            primg8.setSide3(bytes8side3);
            primg8.setProductId(8);
            productImageRepository.save(primg8);
            Product p8 = new Product(
                    8L, "Apple watch 14 pro", "#dcdcdc",
                    "apple", "tools", 300d, "latest apple watch",
                    LocalDate.now(), "medium",730L, 3d, 10D
            );


            File f9side1 = new File("src/main/resources/templates/image/watch2_side1.png");
            FileInputStream fIn9side1 = new FileInputStream(f9side1);
            byte[] bytes9side1 = new byte[(int) f9side1.length()];
            fIn9side1.read(bytes9side1);

            File f9side2 = new File("src/main/resources/templates/image/watch2_side2.png");
            FileInputStream fIn9side2 = new FileInputStream(f9side2);
            byte[] bytes9side2 = new byte[(int) f9side2.length()];
            fIn9side2.read(bytes9side2);

            File f9side3 = new File("src/main/resources/templates/image/watch2_side3.png");
            FileInputStream fIn9side3 = new FileInputStream(f9side3);
            byte[] bytes9side3 = new byte[(int) f9side3.length()];
            fIn9side3.read(bytes9side3);

            ProductSidesImages primg9 = new ProductSidesImages();
            primg9.setSide1(bytes9side1);
            primg9.setSide2(bytes9side2);
            primg9.setSide3(bytes9side3);
            primg9.setProductId(9);
            productImageRepository.save(primg9);
            Product p9 = new Product(
                    9L, "modern stylish watch", "#f7f6f2",
                    "apple", "tools", 99d, "latest apple watch",
                    LocalDate.now(), "mini",200L, 5d, 8D
            );


            File f10side1 = new File("src/main/resources/templates/image/watch3_side1.png");
            FileInputStream fIn10side1 = new FileInputStream(f10side1);
            byte[] bytes10side1 = new byte[(int) f10side1.length()];
            fIn10side1.read(bytes10side1);

            File f10side2 = new File("src/main/resources/templates/image/watch3_side2.png");
            FileInputStream fIn10side2 = new FileInputStream(f10side2);
            byte[] bytes10side2 = new byte[(int) f10side2.length()];
            fIn10side2.read(bytes10side2);

            File f10side3 = new File("src/main/resources/templates/image/watch3_side3.png");
            FileInputStream fIn10side3 = new FileInputStream(f10side3);
            byte[] bytes10side3 = new byte[(int) f10side3.length()];
            fIn10side3.read(bytes10side3);

            ProductSidesImages primg10 = new ProductSidesImages();
            primg10.setSide1(bytes10side1);
            primg10.setSide2(bytes10side2);
            primg10.setSide3(bytes10side3);
            primg10.setProductId(10);
            productImageRepository.save(primg10);
            Product p10 = new Product(
                    10L, "Samsung A71", "#94e3d0",
                    "samsung", "mobile", 500d, "latest samsung phone",
                    LocalDate.now(), "large",1600L, 4d, 5D
            );

            File f11 = new File("src/main/resources/templates/image/headphone.png");
            FileInputStream fIn11 = new FileInputStream(f11);
            byte[] bytes11 = new byte[(int) f11.length()];
            fIn11.read(bytes11);
            ProductSidesImages primg11 = new ProductSidesImages();
            primg11.setSide1(bytes11);
            primg11.setProductId(11);
            productImageRepository.save(primg11);
            Product p11 = new Product(
                    11L, "Modern Headphone", "#4f659f",
                    "jax", "tools", 500d, "latest jax headphone",
                    LocalDate.now(), "large",900L, 5d, 10D
            );

            File f12 = new File("src/main/resources/templates/image/headphone600x.png");
            FileInputStream fIn12 = new FileInputStream(f12);
            byte[] bytes12 = new byte[(int) f12.length()];
            fIn12.read(bytes12);
            ProductSidesImages primg12 = new ProductSidesImages();
            primg12.setSide1(bytes12);
            primg12.setProductId(12);
            productImageRepository.save(primg12);
            Product p12 = new Product(
                    12L, "Headphone 600x", "#c23533",
                    "jax", "tools", 500d, "latest jax headphone",
                    LocalDate.now(), "large",1180L, 4d, 8D
            );
            File f13 = new File("src/main/resources/templates/image/airpods-3.png");
            FileInputStream fIn13 = new FileInputStream(f13);
            byte[] bytes13 = new byte[(int) f13.length()];
            fIn13.read(bytes13);
            ProductSidesImages primg13= new ProductSidesImages();
            primg13.setSide1(bytes13);
            primg13.setProductId(13);
            productImageRepository.save(primg13);
            Product p13 = new Product(
                    13L, "airpods", "#ebecf0",
                    "apple", "tools", 500d, "latest airpod",
                    LocalDate.now(), "large",1700L, 3d, 10D
            );


            File f14 = new File("src/main/resources/templates/image/r9.png");
            FileInputStream fIn14 = new FileInputStream(f14);
            byte[] bytes14 = new byte[(int) f14.length()];
            fIn14.read(bytes14);
            ProductSidesImages primg14 = new ProductSidesImages();
            primg14.setSide1(bytes14);
            primg14.setProductId(14);
            productImageRepository.save(primg14);
            Product p14 = new Product(
                    14L, "r9", "#05c7c9",
                    "Xiaomi", "mobile", 500d, "latest Xiaomi headphone",
                    LocalDate.now(), "large",1400L, 5d, 15D
            );
            File f15 = new File("src/main/resources/templates/image/H10_ID.png");
            FileInputStream fIn15 = new FileInputStream(f15);
            byte[] bytes15 = new byte[(int) f15.length()];
            fIn15.read(bytes15);
            ProductSidesImages primg15 = new ProductSidesImages();
            primg15.setSide1(bytes15);
            primg15.setProductId(15);
            productImageRepository.save(primg15);
            Product p15 = new Product(
                    15L, "H10", "#3cbdbb",
                    "Xiaomi", "mobile", 500d, "latest Xiaomi ",
                    LocalDate.now(), "large",1490L, 3d, 10D
            );

            File f16 = new File("src/main/resources/templates/image/c06.png");
            FileInputStream fIn16 = new FileInputStream(f16);
            byte[] bytes16 = new byte[(int) f16.length()];
            fIn16.read(bytes16);
            ProductSidesImages primg16 = new ProductSidesImages();
            primg16.setSide1(bytes16);
            primg16.setProductId(16);
            productImageRepository.save(primg16);
            Product p16 = new Product(
                    16L, "Laptop c06", "#525252",
                    "HP", "pc", 500d, "latest HP ",
                    LocalDate.now(), "large",120L, 5d, 5D
            );

            File f17 = new File("src/main/resources/templates/image/c9.png");
            FileInputStream fIn17 = new FileInputStream(f17);
            byte[] bytes17 = new byte[(int) f17.length()];
            fIn17.read(bytes17);
            ProductSidesImages primg17 = new ProductSidesImages();
            primg17.setSide1(bytes17);
            primg17.setProductId(17);
            productImageRepository.save(primg17);
            Product p17 = new Product(
                    17L, "Laptop c9", "#edeeec",
                    "HP", "pc", 500d, "latest HP ",
                    LocalDate.now(), "large",100L, 4d, 10D
            );

            File f18 = new File("src/main/resources/templates/image/laptop.png");
            FileInputStream fIn18 = new FileInputStream(f18);
            byte[] bytes18 = new byte[(int) f18.length()];
            fIn18.read(bytes18);
            ProductSidesImages primg18 = new ProductSidesImages();
            primg18.setSide1(bytes18);
            primg18.setProductId(18);
            productImageRepository.save(primg18);
            Product p18 = new Product(
                    18L, "ASUS studio Book", "#494753",
                    "Asus", "pc", 500d, "latest Asus ",
                    LocalDate.now(), "large",70L, 4d, 8D
            );
            File f19 = new File("src/main/resources/templates/image/laptop-acer-1.png");
            FileInputStream fIn19 = new FileInputStream(f19);
            byte[] bytes19 = new byte[(int) f19.length()];
            fIn19.read(bytes19);
            ProductSidesImages primg19 = new ProductSidesImages();
            primg19.setSide1(bytes19);
            primg19.setProductId(19);
            productImageRepository.save(primg19);
            Product p19 = new Product(
                    19L, "acer studio Book", "#aeafb3",
                    "acer", "pc", 500d, "latest acer ",
                    LocalDate.now(), "large",320L, 4d, 10D
            );
            File f20 = new File("src/main/resources/templates/image/philips-takh402.png");
            FileInputStream fIn20 = new FileInputStream(f20);
            byte[] bytes20 = new byte[(int) f20.length()];
            fIn20.read(bytes20);
            ProductSidesImages primg20 = new ProductSidesImages();
            primg20.setSide1(bytes20);
            primg20.setProductId(20);
            productImageRepository.save(primg20);
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
