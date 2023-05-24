package com.testyantraglobal.shopping.shoppingkarttyapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.User;

public interface UserRespsitory extends MongoRepository<User, Integer> {

	public User findUserByToken(String token);

	public User findByEmail(String email);

}
