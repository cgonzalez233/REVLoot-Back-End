package com.Revature.Project3Spring.service;


import com.Revature.Project3Spring.entity.Product;
import com.Revature.Project3Spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product){
        return repository.save(product);
    }

    public Product getProductByName(String name){
        return repository.findByProductName(name);
    }

    public Product getProductById(Long id){
        return repository.findByProductId(id);
    }

    public List<Product> getAllProduct(){
        return repository.findAll();
    }



}
