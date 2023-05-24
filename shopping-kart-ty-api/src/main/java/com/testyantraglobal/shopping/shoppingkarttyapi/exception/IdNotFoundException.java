package com.testyantraglobal.shopping.shoppingkarttyapi.exception;

public class IdNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message = "ID does not exist";

	public IdNotFoundException() {
	}

	public IdNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
