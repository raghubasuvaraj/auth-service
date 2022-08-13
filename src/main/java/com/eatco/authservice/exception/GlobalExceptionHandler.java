package com.eatco.authservice.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eatco.authservice.dto.Message;
import com.eatco.authservice.dto.RestResponse;
import com.eatco.authservice.util.RestHelper;


/**
 * <p>
 * Handle global exceptions.
 * </p>
 * 
 * @author Rajasankar.s
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String ERROR = "Oops!";

	@ExceptionHandler(value = CustomValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleBaseException(Exception ex) {
		log.error(ERROR, ex);
		CustomValidationException validationException = (CustomValidationException) ex;
		ErrorHandle errorHandle = validationException.getErrorCode();
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(errorHandle.getErrorCode());
		errorMessage.setMessage(errorHandle.getMessage());
		return errorMessage;
	}

	@ExceptionHandler({ Exception.class, RuntimeException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleRuntimeException(Exception ex) {
		log.error(ERROR, ex);
		ErrorHandle errorHandle = ErrorCode.EATCO_MANAGEMENT_1001;
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(errorHandle.getErrorCode());
		errorMessage.setMessage(ex.getMessage());
		return errorMessage;
	}
	
	@ExceptionHandler({ ValidationException.class })
	@ResponseBody
	public ResponseEntity<RestResponse> handleValidationException(final ValidationException ex) {
		Message msg = resolveError(ex);
		return RestHelper.responseError(msg.getMsg(), UNAUTHORIZED);
	}

	@ExceptionHandler({ ApplicationException.class })
	@ResponseBody
	public ResponseEntity<RestResponse> handleApplicationException(final ApplicationException ex) {
		Message msg = resolveError(ex);
		return RestHelper.responseError(msg.getMsg(), BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		RestResponse restResponse = new RestResponse();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			restResponse.addError(error.getDefaultMessage());
		});
		
		restResponse.setStatus(false);
		
		return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
	}

	private static Message resolveError(final Exception t) {
		return new Message(t.getMessage());
	}

}