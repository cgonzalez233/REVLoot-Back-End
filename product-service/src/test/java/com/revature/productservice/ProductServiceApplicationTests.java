package com.revature.productservice;

import com.netflix.discovery.converters.Auto;
import com.revature.productservice.controller.ProductController;
import com.revature.productservice.entity.Product;
import com.revature.productservice.repository.ProductRepository;
import com.revature.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProductServiceApplicationTests {
	@Autowired
	private ProductService service;
	private Product[] products = new Product[3];

	@BeforeEach
	void setup(){
		this.products[0] = new Product("File Cabinet",12,"usefull scruber",20,"image url");
		this.products[1] = new Product("Chair",23,"usefull tool",30,"image url");
		this.products[2] = new Product("Box cutter",13,"cut boxes",20,"image url");
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
	void deleteProduct(){
		service.deleteProduct(1L);
		assertThrows(Exception.class, ()-> service.getProductById(1L)) ;
	}

	@Test
	void getProductNames(){
		service.addProducts(products);
		assert(service.getProductNames().contains(products[1].getProductName()));
	}

	@Test
	void updateProduct(){
		Long id = service.addProduct(products[0]).getId();
		service.updateProduct(id, products[1]);
		assert(service.getProductByName(products[1].getProductName()).get(0).getPrice() == products[1].getPrice());
	}

	@Test
	void getProductById(){
		service.addProducts(this.products);
		assert(service.getProductById(Long.parseLong("1")).getProductName().equals(products[0].getProductName()));
	}

	@Test
	void getProductByName(){
		service.addProducts(this.products);
		List<Product> foundproducts = service.getProductByName(this.products[0].getProductName());
		assert(foundproducts.get(0).getPrice() == this.products[0].getPrice());
	}


	@Test
	void contextLoads() {
	}

}
