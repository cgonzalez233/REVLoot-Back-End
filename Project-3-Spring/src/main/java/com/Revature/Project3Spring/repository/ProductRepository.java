package com.Revature.Project3Spring.repository;

import com.Revature.Project3Spring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductName(String name);
    Product findByProductId(Long id);

}