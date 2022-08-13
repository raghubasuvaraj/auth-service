package com.eatco.authservice.enums;

/**
 * Error code while throw the exceptions
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
public enum ErrorCodes {
	
	//Informational responses (100–199)
	CONSTRAINT_VIOLATION(101),
	
	// Client error responses (400–499)
	BAD_REQUEST(400), UNAUTHORIZED(401), ACCESS_DENIED(403), NOT_FOUND(404),
	METHOD_NOT_SUPPORTED(405), CONFLICT(409), INVALID_OPERATION(412), 
	INVALID_CUSTOMER(413), 
	
	// Server error responses (500–599)
	INTERNAL_SERVER_ERROR(500);

	private int code;

	ErrorCodes(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
