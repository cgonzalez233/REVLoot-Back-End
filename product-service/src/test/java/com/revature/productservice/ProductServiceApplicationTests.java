package com.revature.productservice;

import com.netflix.discovery.converters.Auto;
import com.revature.productservice.controller.ProductController;
import com.revature.productservice.entity.Product;
import com.revature.productservice.repository.ProductRepository;
import com.revature.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceApplicationTests {
	@Autowired
	private ProductService service;
	private Product[] products = new Product[3];

	@BeforeEach
	void setup(){
		this.products[0] = new Product("plumbus",12,"usefull scruber",20,"image url");
		this.products[1] = new Product("sleem",23,"usefull tool",30,"image url");
		this.products[2] = new Product("box cutter",13,"cut boxes",20,"image url");
	}

	@Test
	void testAddProduct(){
		assert(this.products[0] == service.addProduct(this.products[0]));
	}

	@Test
	void testAddProducts(){
		assert(this.products == service.addProducts(this.products));
	}

	@Test
	void getProduct(){
		service.addProducts(this.products);
		assert(service.getAllProduct().get(1).getProductName().equals(products[1].getProductName()));
	}

	@Test
	void getProductById(){
		service.addProducts(this.products);
		assert(service.getProductById(Long.parseLong("1")).getProductName().equals(products[0].getProductName()));
	}

	@Test
	void getProductByName(){
		service.addProducts(this.products);
		assert(service.getProductByName(this.products[0].getProductName()).equals(products[0]));
	}


	@Test
	void contextLoads() {
	}

}
