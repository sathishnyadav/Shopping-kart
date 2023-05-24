package com.testyantraglobal.shopping.shoppingkarttyapi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.Test;
import com.testyantraglobal.shopping.shoppingkarttyapi.repository.TestRepository;

@Repository
public class TestDao {
	
	@Autowired
	private TestRepository repository;
	
	public Test saveTest(Test test) {
		return repository.save(test);
	}
	
	public Optional<Test> getTestById(int id) {
		return repository.findById(id);
	}
	
	public List<Test> getAllTest() {
		return repository.findAll();
	}
}
