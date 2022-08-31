package com.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class EshopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopApiApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoderBean(){
		return new BCryptPasswordEncoder();
	}

/*	@Bean
	CommandLineRunner run(ProductService p, UserService userService, RoleRepository roleRepository){
		return args -> {
			User user = new User(null, "Ali", "herawi", (short) 22, LocalDate.of(1999,3,25),"1234","Aliherawi7@gmail.com","image url");
			Role role_user = new Role(1,"USER");
			Role role_admin = new Role(2, "ADMIN");
			roleRepository.save(role_admin);
			roleRepository.save(role_user);
			user.addRole(role_user);
			userService.addUser(user);

			userService.addRoleToUser(user.getEmail(),role_admin.getName());
			Product p1 = new Product(
					null,"a10s","red","img url", "samsung","mobile",99.5,"my mob", LocalDate.now(), "mdeuim"
			);
			p.addProduct(p1);
		};
	}*/


}
