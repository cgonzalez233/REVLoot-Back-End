package com.Revature.Project3Spring.controller;


import com.Revature.Project3Spring.entity.Product;
import com.Revature.Project3Spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAllProduct(){
        return service.getAllProduct();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        return service.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return service.addProduct(product);
    }

}
