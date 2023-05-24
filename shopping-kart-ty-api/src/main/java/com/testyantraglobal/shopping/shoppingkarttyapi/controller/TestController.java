package com.testyantraglobal.shopping.shoppingkarttyapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.ResponseStructure;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.Test;
import com.testyantraglobal.shopping.shoppingkarttyapi.service.TestService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class TestController {

	@Autowired
	private TestService service;

	@ApiOperation(value = "Save the Test data", notes = "Save the Test data and return the Test data with ID")
	@ApiResponses(value = {
	  @ApiResponse(code = 201, message = "Successfully created"),
	  @ApiResponse(code = 401, message = "Not Authorised"),
	})
	@PostMapping(value = "/tests", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Test>> saveTest(@RequestBody Test test) {
		return service.saveTest(test);
	}

	@ApiOperation(value = "Get Test data by ID", notes = "Get's the Test object for the given ID")
	@ApiResponses(value = {
	  @ApiResponse(code = 200, message = "Successfully retrived"),
	  @ApiResponse(code = 401, message = "Not Authorised"),
	  @ApiResponse(code = 404, message = "Given ID does not exist")
	})
	@GetMapping(value="/tests/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Test>> getTest(@PathVariable("id") @ApiParam(name = "id", value = "Test id, in integer", example = "1") int id) {
		return service.getTestById(id);
	}
	
	@ApiOperation(value = "Get all Test data", notes = "Get's all the Test objects as List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived"),
			@ApiResponse(code = 401, message = "Not Authorised")
	})
	@GetMapping(value="/tests",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<List<Test>>> getAllTest() {
		return service.getAllTest();
	}
	
	
}
