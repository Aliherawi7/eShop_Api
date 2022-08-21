package com.eshop;

import com.eshop.model.Product;
import com.eshop.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class EshopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ProductService p){
		return args -> {
			Product p1 = new Product(
					null,"a10s","red","imgrul", "samsung","mobile",99.5,"my mob", LocalDate.now(), "mdeuim"
			);
			p.addProduct(p1);
		};
	}
	/*
	* this.id = id;
        this.name = name;
        this.color = color;
        this.imgUrl = imgUrl;
        this.brandName = brandName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.productionDate = productionDate;
        this.size = size;*/

}
