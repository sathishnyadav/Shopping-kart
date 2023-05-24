package com.testyantraglobal.shopping.shoppingkarttyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.testyantraglobal.shopping.shoppingkarttyapi.dao.TestDao;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.ResponseStructure;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.Test;
import com.testyantraglobal.shopping.shoppingkarttyapi.exception.IdNotFoundException;
import com.testyantraglobal.shopping.shoppingkarttyapi.util.ApplicationConstants;

@Service
public class TestService {
	@Autowired
	private TestDao dao;
	@Autowired
	private SequenceGenerator sequenceGenerator;
	

	
	public ResponseEntity<ResponseStructure<Test>> saveTest(Test test) {
		test.setId((int)sequenceGenerator.generateSequence("test_sequence"));
		ResponseStructure<Test> responseStructure = new ResponseStructure<Test>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage(ApplicationConstants.CREATED);
		responseStructure.setData(dao.saveTest(test));
		return new ResponseEntity<ResponseStructure<Test>>(responseStructure, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<Test>> getTestById(int id) {
		Optional<Test> optional = dao.getTestById(id);
		ResponseStructure<Test> responseStructure = new ResponseStructure<Test>();
		if(!optional.isEmpty()) {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setData(optional.get());
		} else {
			throw new IdNotFoundException("Test id="+id+", does not exist");
		}
		return new ResponseEntity<ResponseStructure<Test>>(responseStructure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Test>>> getAllTest() {
		ResponseStructure<List<Test>> responseStructure = new ResponseStructure<List<Test>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(ApplicationConstants.SUCCESS);
		responseStructure.setData(dao.getAllTest());
		return new ResponseEntity<ResponseStructure<List<Test>>>(responseStructure, HttpStatus.OK);
	}
	
	
}
