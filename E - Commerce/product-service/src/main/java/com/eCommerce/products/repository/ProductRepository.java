package com.eCommerce.products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eCommerce.products.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
