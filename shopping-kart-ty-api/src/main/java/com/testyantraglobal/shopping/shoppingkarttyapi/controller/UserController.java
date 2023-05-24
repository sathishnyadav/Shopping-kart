package com.testyantraglobal.shopping.shoppingkarttyapi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.ResponseStructure;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.User;
import com.testyantraglobal.shopping.shoppingkarttyapi.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Save the User data", notes = "Save the User data and return the User data with ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 400, message = "User data not created"),
			@ApiResponse(code = 401, message = "Not Authorised"), })
	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user, HttpServletRequest request) {
		return userService.saveUser(user, request);
	}

	@ApiOperation(value = "Returns all the Users data", notes = " Returns all users data in the form of list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users data is returned"),
			@ApiResponse(code = 400, message = "User data not received"),
			@ApiResponse(code = 401, message = "Not Authorised") })
	@GetMapping(value = "/users", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })

	public ResponseEntity<ResponseStructure<List<User>>> findAll() {
		return userService.findAll();
	}

	@ApiOperation(value = "Returns single user based on id", notes = " Returns all users data in the form of list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users data is returned"),
			@ApiResponse(code = 400, message = "User data not received") })
	@GetMapping(value = "/user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<User>> findUser(@PathVariable("id") int id) {
		return userService.findUser(id);
	}

	@ApiOperation(value = "Saves the Updated User Data", notes = "Saves the data with existing id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users data is returned"),
			@ApiResponse(code = 401, message = "Not Authorised"),
			@ApiResponse(code = 404, message = "Given ID does not exist") })
	@PutMapping(value = "/users/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user, @PathVariable int id) {
		return userService.updateUser(user, id);
	}

	@ApiOperation(value = "Deletes data from database", notes = "Deletes the data from the database with the given id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users data is returned"),
			@ApiResponse(code = 401, message = "Not Authorised"),
			@ApiResponse(code = 404, message = "Given ID does not exist") })
	@DeleteMapping(value = "/users/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteUser(@PathVariable("id") int id) {
		return userService.deleteUser(id);
	}

	@ApiOperation(value = "URL to verfiy the user account", notes = "URL sent to user mail is verified")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Account Activated"),
			@ApiResponse(code = 400, message = "INVALID URL"), @ApiResponse(code = 401, message = "Not Authorised"),
			@ApiResponse(code = 404, message = "Account not found") })
	@PostMapping(value = "/users/verify-account", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<String>> verifyAccount(@RequestParam String token) {
		return userService.findUserByToken(token);
	}

	@ApiOperation(value = "Resets the password", notes = "Password is reset in the database")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Password reset succesfull"),
			@ApiResponse(code = 400, message = "INVALID URL"),
			@ApiResponse(code = 404, message = "Account not found") })
	@PostMapping(value = "/users/reset-password", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<String>> resetPassWord(@RequestParam("token") String token,
			@RequestParam String new_password) {
		return userService.resetPassword(token, new_password);
	}

	@ApiOperation(value = "Genarates URL for forgot password", notes = "URL sent to user mail to change the user password")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Password reset mail Sent"),
			@ApiResponse(code = 400, message = "INVALID EMAIL"),
			@ApiResponse(code = 404, message = "Account not found") })
	@PostMapping(value = "/users/forgot-password", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<String>> forgotPassWord(@RequestParam String email,
			HttpServletRequest request) {
		return userService.forgotPassWord(email, request);
	}

	@ApiOperation(value = "Returns User by Email and password", notes = "Returns User based on Email and Password ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucessfull"),
			@ApiResponse(code = 400, message = "Invaild User email and Password"),
			@ApiResponse(code = 401, message = "Not Authorised"),
			@ApiResponse(code = 404, message = "Account not found") })
	@GetMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<User>> findUserByEmailAndPassword(
			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
		return userService.findUserByEmailAndPassword(email, password);

	}

}
