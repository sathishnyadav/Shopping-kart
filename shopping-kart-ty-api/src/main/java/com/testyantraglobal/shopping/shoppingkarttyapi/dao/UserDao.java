package com.testyantraglobal.shopping.shoppingkarttyapi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.User;
import com.testyantraglobal.shopping.shoppingkarttyapi.repository.UserRespsitory;

@Repository
public class UserDao {

	@Autowired
	private UserRespsitory userRespsitory;

	public User saveUser(User user) {
		return userRespsitory.save(user);
	}

	public List<User> getAllUser() {
		return userRespsitory.findAll();
	}

	public Optional<User> findUser(int id) {
		return userRespsitory.findById(id);
	}

	public User updateUser(User user, int id) {
		User revUser = findUser(id).get();
		if (revUser != null) {
			user.setId(id);
			return userRespsitory.save(user);
		}
		return null;

	}

	public void deleteUser(int id) {
		userRespsitory.deleteById(id);
	}

	public User findUserByToken(String token) {
		return userRespsitory.findUserByToken(token);
	}

	public User findUserByEmail(String email) {
		return userRespsitory.findByEmail(email);
	}
}
