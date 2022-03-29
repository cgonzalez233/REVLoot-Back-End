package com.revature.productservice.entity;

import lombok.*;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;
    private double price;
    private String description;
    private int productQty;
    private String productImage;
}
