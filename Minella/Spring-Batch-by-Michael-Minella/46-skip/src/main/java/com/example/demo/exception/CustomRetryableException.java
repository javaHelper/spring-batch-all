package com.example.demo.exception;


import java.io.Serial;

public class CustomRetryableException extends Exception {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public CustomRetryableException() {
		super();
	}

	public CustomRetryableException(String msg) {
		super(msg);
	}
}
