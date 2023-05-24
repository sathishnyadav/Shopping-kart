package com.testyantraglobal.shopping.shoppingkarttyapi.dto;

import lombok.Data;

@Data
public class ResponseStructure<T> {
	private int statusCode;
	private String message;
	private T data;

}
