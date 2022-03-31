package com.revature.productservice;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.productservice.entity.Product;
import com.revature.productservice.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(ProductService productService) {
		return args -> {
			//read JSON and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/products.json");
			try {
				List<Product> products = mapper.readValue(inputStream, typeReference);
				productService.saveToDb(products);
				System.out.println("Products saved");
			} catch (IOException e){
				System.out.println("Unable to save products: " + e.getMessage());
			}

		};

	}

}
