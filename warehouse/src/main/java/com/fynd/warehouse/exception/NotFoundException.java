package com.fynd.warehouse.exception;


public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	NotFoundException(String message){
		super(message);
	}
	

}