package com.testyantraglobal.shopping.shoppingkarttyapi.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.testyantraglobal.shopping.shoppingkarttyapi.dao.UserDao;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.EmailConfiguration;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.ResponseStructure;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.User;
import com.testyantraglobal.shopping.shoppingkarttyapi.exception.IdNotFoundException;
import com.testyantraglobal.shopping.shoppingkarttyapi.util.ApplicationConstants;
import com.testyantraglobal.shopping.shoppingkarttyapi.util.UserStatus;

@Service
public class UserService {
	@Autowired
	private ShoppingKartTyApplicationEmailService emailService;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private EmailConfiguration configuration;

	@Autowired
	private UserDao userDao;

	public ResponseEntity<ResponseStructure<User>> saveUser(User user, HttpServletRequest request) {
		user.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
		user.setStatus(UserStatus.IN_ACTIVE.toString());
		User reUser = userDao.saveUser(user);
		ResponseStructure<User> responseStructure = new ResponseStructure<User>();
		if (reUser != null) {

			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage(ApplicationConstants.CREATED);
			responseStructure.setData(reUser);
			configuration.setSubject(ApplicationConstants.SUBJECT);
			configuration.setTemplate(ApplicationConstants.TEMPLATE);
			Map<String, String> emailinfo = new LinkedHashMap<String, String>();
			if (user.getLastName() != null) {
				emailinfo.put("name", user.getFirstName() + " " + user.getLastName());
			} else {
				emailinfo.put("name", user.getFirstName());
			}
			emailinfo.put("email", user.getEmail());
			configuration.setUser(emailinfo);
			emailService.sendWelcomeEmail(configuration, request, user);
			return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.CREATED);
		} else {
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage(ApplicationConstants.NO_USER);
			responseStructure.setData(null);

			return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<User>> updateUser(User user, int id) {
		ResponseStructure<User> responseStructure = new ResponseStructure<User>();
		if (userDao.findUser(id).get().getStatus().equals(UserStatus.ACTIVE.toString())) {
			user.setStatus(UserStatus.ACTIVE.toString());
			User reUser = userDao.updateUser(user, id);
			if (reUser != null) {

				responseStructure.setStatusCode(HttpStatus.OK.value());
				responseStructure.setMessage(ApplicationConstants.CREATED);
				responseStructure.setData(reUser);
				return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.CREATED);
			} else {
				responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
				responseStructure.setMessage(ApplicationConstants.NO_USER);
				responseStructure.setData(null);

				return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.NOT_FOUND);
			}
		} else {
			responseStructure.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			responseStructure.setMessage(ApplicationConstants.INACTIVE_USER);
			responseStructure.setData(null);

			return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.UNAUTHORIZED);
		}
	}

	public ResponseEntity<ResponseStructure<List<User>>> findAll() {
		List<User> receivedUsers = userDao.getAllUser();
		ResponseStructure<List<User>> responseStructure = new ResponseStructure<List<User>>();
		HttpStatus httpStatus = null;
		if (receivedUsers.isEmpty()) {
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage(ApplicationConstants.ID_NOT_FOUND);
			responseStructure.setData(null);
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setData(receivedUsers);
			httpStatus = HttpStatus.FOUND;
		}
		return new ResponseEntity<ResponseStructure<List<User>>>(responseStructure, httpStatus);
	}

	public ResponseEntity<ResponseStructure<User>> findUser(int id) {
		User receivedUser = userDao.findUser(id).get();
		ResponseStructure<User> responseStructure = new ResponseStructure<User>();

		if (receivedUser == null) {
			throw new IdNotFoundException();
		} else {
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage(ApplicationConstants.SUCCESS);
			responseStructure.setData(receivedUser);
		}
		return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<String>> deleteUser(int id) {
		Optional<User> receivedUser = userDao.findUser(id);

		ResponseStructure<String> responseStructure = new ResponseStructure<String>();

		if (receivedUser.isEmpty()) {
			throw new IdNotFoundException();
		} else {
			userDao.deleteUser(id);
			responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
			responseStructure.setMessage(ApplicationConstants.DELETED_USER);
			responseStructure.setData("User Deleted Sucessfully");
		}
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<String>> findUserByToken(String token) {
		User user = userDao.findUserByToken(token);
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		if (user != null) {
			user.setStatus(UserStatus.ACTIVE.toString());
			user.setToken(null);
			userDao.saveUser(user);
			responseStructure.setData("Account Activated");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Verified");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		} else {
			responseStructure.setData("INVALID URL");
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("IN VAILD USER");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<ResponseStructure<String>> resetPassword(String token, String new_password) {
		User user = userDao.findUserByToken(token);
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		if (user != null) {
			user.setPassword(new_password);
			user.setStatus(UserStatus.ACTIVE.toString());
			user.setToken(null);
			userDao.saveUser(user);
			responseStructure.setData("Password reset succesfull");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Password Reset succesful");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		} else {
			responseStructure.setData("INVALID URL");
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("IN VAILD USER");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<String>> forgotPassWord(String email, HttpServletRequest request) {
		User user = userDao.findUserByEmail(email);
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		if (user != null) {
			user.setStatus(UserStatus.IN_ACTIVE.toString());
			configuration.setSubject(ApplicationConstants.FORGOT_PASSWORD);
			configuration.setTemplate(ApplicationConstants.RESET_PASSWORD_TEMPLATE);
			Map<String, String> emailinfo = new LinkedHashMap<String, String>();
			if (user.getLastName() != null) {
				emailinfo.put("name", user.getFirstName() + " " + user.getLastName());
			} else {
				emailinfo.put("name", user.getFirstName());
			}
			emailinfo.put("email", user.getEmail());
			configuration.setUser(emailinfo);
			responseStructure.setData("Password reset mail Sent");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Password Reset mail sent succesful");
			emailService.sendResetPasswordEmail(configuration, user, request);

			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		} else {
			responseStructure.setData("INVALID EMAIL");
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("Register to Portal");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<User>> findUserByEmailAndPassword(String email, String password) {
		User user = userDao.findUserByEmail(email);
		ResponseStructure<User> responseStructure = new ResponseStructure<User>();
		if (user.getPassword().equals(password)) {
			if (user.getStatus().equals(UserStatus.ACTIVE.toString())) {
				responseStructure.setData(user);
				responseStructure.setStatusCode(HttpStatus.OK.value());
				responseStructure.setMessage("Sucess");
				return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.OK);
			} else {
				responseStructure.setData(null);
				responseStructure.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				responseStructure.setMessage("Verify Email sent to the mail");
				return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.UNAUTHORIZED);
			}
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("Invaild User email and Password");
			return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.NOT_FOUND);
		}

	}
}
