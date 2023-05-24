package com.testyantraglobal.shopping.shoppingkarttyapi.exception;

public class EmailNotSentException extends RuntimeException {

	private String message = "Email Not Sent ";

	public EmailNotSentException() {
	}

	private static final long serialVersionUID = 1L;

	public EmailNotSentException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return message;
	}

}
