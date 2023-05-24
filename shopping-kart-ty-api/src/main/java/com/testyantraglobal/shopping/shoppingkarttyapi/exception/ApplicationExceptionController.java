package com.testyantraglobal.shopping.shoppingkarttyapi.exception;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.testyantraglobal.shopping.shoppingkarttyapi.dto.ResponseStructure;
import com.testyantraglobal.shopping.shoppingkarttyapi.util.ApplicationConstants;

@ControllerAdvice
public class ApplicationExceptionController extends ResponseEntityExceptionHandler  {
	
	@ExceptionHandler(value = IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage(ApplicationConstants.ID_NOT_FOUND);
		responseStructure.setData(exception.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = EmailNotSentException.class)
	public ResponseEntity<ResponseStructure<String>> handleMessagingException(MessagingException messagingException) {
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		responseStructure.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		responseStructure.setMessage(ApplicationConstants.EMAIL_NOT_SENT);
		responseStructure.setData(messagingException.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	
}
