package com.bsp.exceptions;
/**
 * 用户异常信息
 * @author wjb
 *
 */
public class UserDefinedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDefinedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDefinedException(String message) {
		super(message);
	}
	
}
