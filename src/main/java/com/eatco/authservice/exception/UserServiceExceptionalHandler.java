package com.eatco.authservice.exception;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eatco.authservice.enums.ErrorCodes;



/**
 * Create a custom exceptional handler, customised exceptional model used
 * instead of the ResponseEntity.class
 * 
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
@ControllerAdvice
@RestController
public class UserServiceExceptionalHandler {

	/**
	 * Returns custom error response on occurrence of custom exception.
	 * 
	 * @param e
	 * @param response
	 * @return
	 */
	@ExceptionHandler(value = USException.class)
	public ErrorResponse handleContentNotFoundException(USException e, HttpServletResponse response) {
		response.setStatus(e.getErrorCode());
		ErrorResponse error = new ErrorResponse();
		error.setMessage(e.getMessage());
		error.setErrorCode(e.getErrorCode());
		error.setSuccess(false);
		return error;
	}

	/**
	 * Returns custom error response on occurrence of data integrity violation
	 * exception.
	 *
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ErrorResponse handleDataIntegrityException(ConstraintViolationException e) {
		ErrorResponse error = new ErrorResponse();
		String message = e.getCause().getMessage();
		if (message != null && message.contains("=")) {
			String[] messages = message.split("=");
			if (messages.length == 2) {
				String value = messages[1];
				int startIndex = value.indexOf('(') + 1;
				int endIndex = value.lastIndexOf(')');
				value = value.substring(startIndex, endIndex);
				if (value.contains(",")) {
					String[] values = value.split(",");
					message = values[0] + " already exists";
				} else {
					message = value + " already exists";
				}

			}
		}
		error.setMessage(message == null ? "Constraint violation" : message);
		error.setErrorCode(ErrorCodes.CONSTRAINT_VIOLATION.getCode());
		error.setSuccess(false);
		return error;
	}

	/**
	 * Returns custom error response on occurrence spring validation exception.
	 *
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		ErrorResponse error = new ErrorResponse();
		StringBuilder builder = new StringBuilder();
		for (FieldError fError : fieldErrors) {
			if (!builder.toString().isEmpty()) {
				builder.append(", ");
			}
			builder.append(fError.getField());
			builder.append(" ");
			builder.append(fError.getDefaultMessage());
		}
		error.setErrorCode(ErrorCodes.BAD_REQUEST.getCode());
		error.setMessage(builder.toString());
		error.setSuccess(false);
		return error;
	}

}
