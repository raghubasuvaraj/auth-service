package com.eatco.authservice.exception;

/**
 * <p>
 * Handle error messages.
 * </p>
 * 
 * @author Rajasankar
 *
 */
public class ErrorMessage {

	private int errorCode;

	private String message;

	public ErrorMessage() {
		super();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorMessage [errorCode=" + errorCode + ", message=" + message + "]";
	}

}
