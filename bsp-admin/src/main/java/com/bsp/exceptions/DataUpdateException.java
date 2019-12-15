package com.bsp.exceptions;

public class DataUpdateException extends RuntimeException  {
	
private static final long serialVersionUID = 1L;
	
	public DataUpdateException(String message) {
		super(message);
	}

	public DataUpdateException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
