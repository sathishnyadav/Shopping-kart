package com.testyantraglobal.shopping.shoppingkarttyapi.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "product")
@Data
public class Product {
	@Transient
	public static final String SEQUENCE_NAME = "product_sequence";
	@Id
	private long id;
	private String name;
	private String title;
	private double cost;
	private double offer;
	private String type;
	private String brand;
	private int merchant_id;
	private String image_link;
}
