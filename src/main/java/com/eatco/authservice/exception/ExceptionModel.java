package com.eatco.authservice.exception;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.threeten.bp.LocalDateTime;

import lombok.Data;

@Data
public class ExceptionModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * The {@code status} field used to hold HTTP Statuses
	 */
	private HttpStatus status;
	
	/**
	 * The {@code errors} field used to get a list of errors thrown
	 */
    private List<String> errors;
    
    /**
     * The {@code timeStamp} field used to get the Date and Time the error occurred
     */
    private LocalDateTime timeStamp;

    /**
     * The {@code pathUri} field used to get the API path where the issue has occurred
     */
    private String pathUri;
    
}

