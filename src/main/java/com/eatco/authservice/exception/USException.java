package com.eatco.authservice.exception;

import com.eatco.authservice.enums.ErrorCodes;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Prithvi Yekkaladevi 
 * @author Arya C Achari
 * @modifiedOn 14-Mar-2022
 * @since 0.0.1
 *
 */
@Getter
@Setter
public class USException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	private String message;
	
	public USException(String message) {
		super(message);
		this.message = message;
	}
	
	public USException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public USException(ErrorCodes errorCode, String message) {
		super(message);
		this.errorCode = errorCode.getCode();
		this.message = message;
	}
	
	public USException(Integer errorCode, Exception ex) {
		super(ex);
		this.errorCode = errorCode;
		this.message = ex.getMessage();
	}
	
	public USException(Exception ex) {
		super(ex);
		this.message = ex.getMessage();
	}

}
