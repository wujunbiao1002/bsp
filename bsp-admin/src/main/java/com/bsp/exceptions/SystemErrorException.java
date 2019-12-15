package com.bsp.exceptions;

public class SystemErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SystemErrorException(String message) {
		super(message);
	}

	public SystemErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
