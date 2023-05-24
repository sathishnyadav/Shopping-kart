package com.testyantraglobal.shopping.shoppingkarttyapi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.Product;
import com.testyantraglobal.shopping.shoppingkarttyapi.exception.IdNotFoundException;
import com.testyantraglobal.shopping.shoppingkarttyapi.repository.ProductRepository;

@Repository
public class ProductDao {
	@Autowired
	ProductRepository productRepository;

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	public Optional<Product> findProduct(int id) {
		return productRepository.findById(id);
	}

	public Product updateProduct(int id, Product product) {
		Optional<Product> revProduct = findProduct(id);
		if (revProduct.isEmpty()) {
			throw new IdNotFoundException();
		}
		product.setId(id);
		return productRepository.save(product);
	}

	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Product> findProductByType(String type) {
		return productRepository.findProductsByType(type);
	}

	public List<Product> findProductByBrand(String brand) {
		return productRepository.findProductsByBrand(brand);
	}
}
