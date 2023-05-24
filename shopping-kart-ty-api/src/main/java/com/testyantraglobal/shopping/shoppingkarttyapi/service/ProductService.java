package com.testyantraglobal.shopping.shoppingkarttyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.testyantraglobal.shopping.shoppingkarttyapi.dao.ProductDao;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.Product;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.ResponseStructure;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.User;
import com.testyantraglobal.shopping.shoppingkarttyapi.exception.IdNotFoundException;
import com.testyantraglobal.shopping.shoppingkarttyapi.util.ApplicationConstants;

@Service
public class ProductService {
	@Autowired
	ProductDao productDao;
	@Autowired
	private SequenceGenerator sequenceGenerator;

	public ResponseEntity<ResponseStructure<Product>> saveProduct(Product product) {
		ResponseStructure<Product> structure = new ResponseStructure<Product>();
		product.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
		product = productDao.saveProduct(product);
		structure.setMessage(ApplicationConstants.CREATED);
		structure.setStatusCode(HttpStatus.CREATED.value());
		structure.setData(product);
		return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Product>> updateProduct(Product product, int id) {
		ResponseStructure<Product> responseStructure = new ResponseStructure<Product>();
		HttpStatus httpStatus = null;
		Product reProduct = productDao.updateProduct(id, product);
		if (reProduct != null) {

			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage(ApplicationConstants.CREATED);
			responseStructure.setData(reProduct);
			httpStatus = HttpStatus.FOUND;
		} else {
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage(ApplicationConstants.NO_PRODUCT);
			responseStructure.setData(null);
			httpStatus = HttpStatus.FOUND;
		}
		return new ResponseEntity<ResponseStructure<Product>>(responseStructure, httpStatus);
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findAll() {
		List<Product> receivedProducts = productDao.findAll();
		ResponseStructure<List<Product>> responseStructure = new ResponseStructure<List<Product>>();
		HttpStatus httpStatus = null;
		if (receivedProducts.isEmpty()) {
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage(ApplicationConstants.ID_NOT_FOUND);
			responseStructure.setData(null);
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setData(receivedProducts);
			httpStatus = HttpStatus.FOUND;
		}
		return new ResponseEntity<ResponseStructure<List<Product>>>(responseStructure, httpStatus);
	}

	public ResponseEntity<ResponseStructure<Optional<Product>>> findProduct(int id) {
		Optional<Product> receivedProduct = productDao.findProduct(id);
		ResponseStructure<Optional<Product>> responseStructure = new ResponseStructure<Optional<Product>>();
		HttpStatus httpStatus = null;
		System.out.println(receivedProduct);

		if (receivedProduct.isEmpty()) {
			throw new IdNotFoundException();
		} else {
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setData(receivedProduct);
			httpStatus = HttpStatus.FOUND;
		}
		return new ResponseEntity<ResponseStructure<Optional<Product>>>(responseStructure, httpStatus);
	}

	public ResponseEntity<ResponseStructure<String>> deleteProduct(int id) {
		Optional<Product> receivedProduct = productDao.findProduct(id);

		ResponseStructure<String> responseStructure = new ResponseStructure<String>();

		if (receivedProduct.isEmpty()) {
			throw new IdNotFoundException();
		} else {
			productDao.deleteProduct(id);
			responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setData("DELETED");
		}
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByType(String type) {
		List<Product> receivedProducts = productDao.findProductByType(type);
		ResponseStructure<List<Product>> responseStructure = new ResponseStructure<List<Product>>();
		HttpStatus httpStatus = null;
		if (receivedProducts.isEmpty()) {
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage(ApplicationConstants.TYPE_NOT_FOUND);
			responseStructure.setData(null);
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setData(receivedProducts);
			httpStatus = HttpStatus.FOUND;
		}
		return new ResponseEntity<ResponseStructure<List<Product>>>(responseStructure, httpStatus);
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByBrand(String brand) {
		List<Product> receivedProducts = productDao.findProductByBrand(brand);
		ResponseStructure<List<Product>> responseStructure = new ResponseStructure<List<Product>>();
		HttpStatus httpStatus = null;
		if (receivedProducts.isEmpty()) {
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage(ApplicationConstants.BRAND_NOT_FOUND);
			responseStructure.setData(null);
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setData(receivedProducts);
			httpStatus = HttpStatus.FOUND;
		}
		return new ResponseEntity<ResponseStructure<List<Product>>>(responseStructure, httpStatus);
	}
}
