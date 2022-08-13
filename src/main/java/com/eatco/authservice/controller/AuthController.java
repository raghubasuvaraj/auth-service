package com.eatco.authservice.controller;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatco.authservice.config.JWTToken;
import com.eatco.authservice.dto.RestResponse;
import com.eatco.authservice.dto.UserDto;
import com.eatco.authservice.exception.ApplicationException;
import com.eatco.authservice.model.User;
import com.eatco.authservice.service.AuthService;
import com.eatco.authservice.util.RestHelper;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<RestResponse> signUp(@Valid @RequestBody UserDto userDto) {
		User user = authService.signUp(userDto);
		return RestHelper.responseUserIdAndMessage(user.getId(), "User has been registered successfully!");
	}
	
	@PostMapping("/email/check")
	public ResponseEntity<RestResponse> checkEmail(@RequestBody JSONObject jsonObject) {
		String email = (String) jsonObject.get("email");
		if (email.isEmpty()) {
			throw new ApplicationException("Email cannot be empty!");
		}
		authService.checkEmail(email);
		return RestHelper.responseMessage("Email does not exist", HttpStatus.OK);
	}

	@PostMapping("/email/validate")
	public ResponseEntity<RestResponse> validateEmail(@RequestBody JSONObject jsonObject) {
		String email = (String) jsonObject.get("email");
		if (email.isEmpty()) {
			throw new ApplicationException("Email cannot be empty!");
		}
		authService.validateEmail(email);
		return RestHelper.responseMessage("Email validated successfully!", HttpStatus.OK);
	}

	@PostMapping("/login")
	public JWTToken login(@RequestBody UserDto userDto) {
		return authService.login(userDto);
	}

	@PostMapping("/send/token/password")
	public ResponseEntity<RestResponse> sendTokenForPassword(@RequestBody JSONObject jsonObject) {
		String email = (String) jsonObject.get("email");
		if (email.isEmpty()) {
			throw new ApplicationException("Email cannot be empty!");
		}
		
		int i = authService.sendTokenForResetPassword(email);
		if (i == 0) {
			return RestHelper.responseError("There was an error while sending email!", HttpStatus.BAD_REQUEST);
		} else {
			return RestHelper.responseMessage("Email has been sent successfully!", HttpStatus.OK);
		}
	}
	
	@PostMapping("/send/token")
	public ResponseEntity<RestResponse> sendToken(@RequestBody JSONObject jsonObject) {
		String email = (String) jsonObject.get("email");
		if (email.isEmpty()) {
			throw new ApplicationException("Email cannot be empty!");
		}
		
		int i = authService.sendTokenInEmail(email);
		if (i == 0) {
			return RestHelper.responseError("There was an error while sending email!", HttpStatus.BAD_REQUEST);
		} else {
			return RestHelper.responseMessage("Email has been sent successfully!", HttpStatus.OK);
		}
	}

	@PostMapping("/verify/token")
	public ResponseEntity<RestResponse> verifyToken(@RequestBody JSONObject jsonObject) {
		String token = (String) jsonObject.get("token");
		if (token.isEmpty()) {
			throw new ApplicationException("Token cannot be empty!");
		}
		authService.verifyToken(token);
		return RestHelper.responseMessage("Token verification successful!", HttpStatus.OK);
	}

	@PostMapping("/update/password")
	public ResponseEntity<RestResponse> updatePassword(@RequestBody JSONObject jsonObject) {
		String email = (String) jsonObject.get("email");
		if (email.isEmpty()) {
			throw new ApplicationException("Email cannot be empty!");
		}
		String password = (String) jsonObject.get("password");
		if (password.isEmpty()) {
			throw new ApplicationException("Password cannot be empty!");
		}
		String confirmPassword = (String) jsonObject.get("confirmPassword");
		if (confirmPassword.isEmpty()) {
			throw new ApplicationException("Confirm Password cannot be empty!");
		}
		if (!password.equals(confirmPassword)) {
			throw new ApplicationException("Password & Confirm Password do not match!");
		}
		authService.updatePassword(email, password);
		return RestHelper.responseMessage("Password reset successful!", HttpStatus.OK);
	}

}

