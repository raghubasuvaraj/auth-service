package com.eatco.authservice.exception;

import lombok.Data;

@Data
public class ErrorResponse {
	private int errorCode;

	private String message;

	private Boolean success;

	private Object errorData;
}
