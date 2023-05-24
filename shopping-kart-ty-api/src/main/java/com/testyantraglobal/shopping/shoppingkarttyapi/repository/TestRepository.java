package com.testyantraglobal.shopping.shoppingkarttyapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.Test;

public interface TestRepository extends MongoRepository<Test, Integer> {

}
