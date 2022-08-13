package com.eatco.authservice.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eatco.authservice.dto.RestResponse;

public class RestHelper {

	public static ResponseEntity<RestResponse> responseSuccess(String payloadKey, Object payload) {
		RestResponse restResponse = new RestResponse();
		if (!StringUtils.isEmpty(payloadKey))
			restResponse.addPayload(payloadKey, payload);
		restResponse.setStatus(true);

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	public static ResponseEntity<RestResponse> responseError(String msg, HttpStatus httpStatus) {
		RestResponse restResponse = new RestResponse();
		restResponse.setError(msg);
		restResponse.setStatus(false);

		return new ResponseEntity<>(restResponse, httpStatus);
	}

	public static ResponseEntity<RestResponse> responseMessage(String msg, HttpStatus httpStatus) {
		RestResponse restResponse = new RestResponse();
		restResponse.setMessage(msg);
		restResponse.setStatus(true);

		return new ResponseEntity<>(restResponse, httpStatus);
	}

	public static ResponseEntity<RestResponse> responseUserIdAndMessage(Long userId, String msg) {
		RestResponse restResponse = new RestResponse();
		restResponse.setMessage(msg);
		restResponse.setStatus(true);
		restResponse.setUserId(userId);

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	public static ResponseEntity<RestResponse> qqfesc80rqna4ljmv0ga2m5m6f58twm7nqc27h0gx2(String logoUrl, String msg) {
		RestResponse restResponse = new RestResponse();
		restResponse.setMessage(msg);
		restResponse.setStatus(true);
		restResponse.setLogoUrl(logoUrl);

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}


}
