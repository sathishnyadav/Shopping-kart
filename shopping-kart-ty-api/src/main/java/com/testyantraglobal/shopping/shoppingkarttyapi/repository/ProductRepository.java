package com.testyantraglobal.shopping.shoppingkarttyapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.Product;

public interface ProductRepository extends MongoRepository<Product, Integer> {

	public List<Product> findProductsByType(String type);

	public List<Product> findProductsByBrand(String brand);
}
