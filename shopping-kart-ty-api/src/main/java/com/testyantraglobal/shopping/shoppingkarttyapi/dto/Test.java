package com.testyantraglobal.shopping.shoppingkarttyapi.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "test")
@Data
public class Test {
	@Transient
    public static final String SEQUENCE_NAME = "test_sequence";
	@Id
	private int id;
	private String name;
	private double percentage;
}
