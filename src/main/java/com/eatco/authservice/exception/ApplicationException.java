package com.eatco.authservice.exception;

public class ApplicationException extends RuntimeException{

	private static final long serialVersionUID = -7145481594419137795L;

	public ApplicationException() {
		super();
	}
	
	public ApplicationException(final String message) {
		super(message);
	}
}
