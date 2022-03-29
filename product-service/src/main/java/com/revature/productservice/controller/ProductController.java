package com.revature.productservice.controller;

import com.revature.productservice.entity.Product;
import com.revature.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAllProduct() {
        return service.getAllProduct();
    }

    @GetMapping("/get/{productName}")
    public Product getProductByName(@PathVariable("productName") String productName) {
        return service.getProductByName(productName);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return service.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @PostMapping("/add")
    public Product[] addProducts(@RequestBody Product[] product){
        return service.addProducts(product);
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        service.updateProduct(id, product);
        return "product updated successfully";
    }
}
