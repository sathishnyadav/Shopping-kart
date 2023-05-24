package com.testyantraglobal.shopping.shoppingkarttyapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.Product;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.ResponseStructure;
import com.testyantraglobal.shopping.shoppingkarttyapi.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ProductController {
	@Autowired
	ProductService productService;

	@ApiOperation(value = "Save the product data", notes = "Save the product data and return the product data with ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 400, message = "Product data not created") })
	@PostMapping(value = "/product", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Product>> saveProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}

	@ApiOperation(value = "Saves the Updated Product Data", notes = "Saves the data with existing id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product data is returned"),
			@ApiResponse(code = 400, message = "Invalid product Id") })
	@PutMapping(value = "/product", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody Product product,
			@RequestParam int id) {
		return productService.updateProduct(product, id);
	}

	@ApiOperation(value = "Returns all the products", notes = "will find and return all the products")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "all the products data is returned"),
			@ApiResponse(code = 400, message = "no product is returned") })
	@GetMapping(value = "/product", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<List<Product>>> findAll() {
		return productService.findAll();
	}

	@ApiOperation(value = "returns single product based on  id", notes = "returns the product ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "product is returned"),
			@ApiResponse(code = 404, message = "product with the passed id not found") })
	@GetMapping(value = "/product/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, })
	public ResponseEntity<ResponseStructure<Optional<Product>>> findProduct(@PathVariable int id) {
		return productService.findProduct(id);
	}

	@DeleteMapping(value = "/product/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, })
	@ApiOperation(value = "deletes the product based on id", notes = "delete the product from the database with the give id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "product deleted"),
			@ApiResponse(code = 404, message = "product with the given ID not found") })
	public ResponseEntity<ResponseStructure<String>> deleteProduct(@PathVariable int id) {
		return productService.deleteProduct(id);
	}

	@ApiOperation(value = "returns all the products based on type", notes = "returns all the products in the database with the type passed")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "all the products data with the given type recieved"),
			@ApiResponse(code = 404, message = "products with the given type not returned") })
	@GetMapping("/product/type/{type}")
	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByType(@PathVariable String type) {
		return productService.findProductsByType(type);
	}

	@ApiOperation(value = "returns all the products based on brand", notes = "returns all the products in the database with the barnd passed")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "all the products data with the given brand recieved"),
			@ApiResponse(code = 404, message = "products with the given barnd not returned") })
	@GetMapping("/product/brand/{brand}")
	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByBrand(@PathVariable String brand) {
		return productService.findProductsByBrand(brand);
	}
}
