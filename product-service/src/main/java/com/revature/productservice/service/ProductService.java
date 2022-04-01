package com.revature.productservice.service;

import com.revature.productservice.entity.Product;
import com.revature.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product){
        return repository.save(product);

    }

    // Add all the products onto the database from JSON array
    public Product[] addProducts(Product product[]){
        for(Product products : product){
            repository.save(products);
        }
        return product;
    }

    public List<Product> getProductByName(String name){
        return repository.findByProductName(name);
    }

    public Product getProductById(Long id){
        return repository.findById(id).get();
    }

    public List<Product> getAllProduct(){
        return repository.findAll();
    }

    public void updateProduct(Long id, Product product){
        Product productDb = repository.findById(id).get();
        productDb.setProductName(product.getProductName());
        productDb.setProductQty(product.getProductQty());
        productDb.setPrice(product.getPrice());
        productDb.setDescription(product.getDescription());
        repository.save(productDb);
    }

    public void deleteProduct(Long id){
        repository.deleteById(id);
    }


    public Iterable<Product> saveToDb(List<Product> product) {
        return repository.saveAll(product);
    }

        public List<String> getProductNames () {
            List<Product> productList = repository.findAll();

            List<String> stringList = productList.stream()
                    .map(Product::getProductName)
                    .collect(Collectors.toList());

            return stringList;
        }



    }
}
