package com.eatco.authservice.exception;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = -1893304060806007292L;
	
	public ValidationException() {
		super();
	}
	
	public ValidationException(final String message) {
		super(message);
	}
	
}
