package com.eatco.authservice.service.impl;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eatco.authservice.config.JWTToken;
import com.eatco.authservice.config.JwtTokenProvider;
import com.eatco.authservice.dto.UserDto;
import com.eatco.authservice.exception.ApplicationException;
import com.eatco.authservice.exception.ValidationException;
import com.eatco.authservice.model.User;
import com.eatco.authservice.model.UserPasswordResetToken;
import com.eatco.authservice.repository.UserPasswordResetRepository;
import com.eatco.authservice.repository.UserRepository;
import com.eatco.authservice.service.AuthService;
import com.eatco.authservice.service.SendEmailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  ModelMapper modelmapper;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private  JwtTokenProvider jwtTokenProvider;
	@Autowired
	private  AuthenticationManager authenticationManager;
	@Autowired
	private  SendEmailService emailService;
	@Autowired
	private  UserPasswordResetRepository userPasswordResetRepository;

	@Override
	public User signUp(UserDto userDto) {
		Boolean isUserAlreadyExists = userRepository.existsByEmail(userDto.getEmail());
		User user = null;
		Random rnd = new Random();
		int number = rnd.nextInt(9999);
		String random4Digits = String.format("%04d", number);
		try {
			user = modelmapper.map(userDto, User.class);
			if (!isUserAlreadyExists) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setReferralCode(user.getName().substring(0, 4).toUpperCase() + random4Digits);
				user.setActive(true);
				user = userRepository.save(user);
			} else {
				throw new ApplicationException("Email already exists!");
			}
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
		return user;
	}

	@Override
	public JWTToken login(UserDto userDto) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
			JWTToken jwtToken = jwtTokenProvider.createToken(authentication);
			return jwtToken;
		} catch (Exception e) {
			log.error("Login failed for user {} :: {}", userDto.getEmail(), e.getMessage());
			throw new ValidationException("Invalid username/password!");
		}
	}

	@Override
	public void validateEmail(String email) {
		if (!userRepository.existsByEmail(email)) {
			throw new ApplicationException("Invalid Email!");
		}
	}

	@Override
	public int sendTokenForResetPassword(String userEmail) {
		int i = 0;
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		String token = String.format("%06d", number);
		try {
			User user = userRepository.findByEmail(userEmail);
			if (!Objects.nonNull(user)) {
				throw new ApplicationException("User Not Found");
			}
			emailService.createPasswordResetTokenForUser(user, token);
			i = emailService.constructResetTokenEmail(token, user);

		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
		return i;
	}

	@Override
	public void verifyToken(String token) {
		try {
			
			
			UserPasswordResetToken userPasswordResetToken = userPasswordResetRepository.findByToken(token);
			if (!isTokenFound(userPasswordResetToken)) {
				throw new ApplicationException("Invalid Token!");
			}
			if (isTokenExpired(userPasswordResetToken)) {
				throw new ApplicationException("Token expired!");
			}
			log.info("Token is verified successfully!");
		} catch (Exception e) {
			log.error("Error in verifying token for user. Exception: {} ", e.getMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public void updatePassword(String email, String password) {
		try {
			User user = userRepository.findByEmail(email);
			if (!Objects.nonNull(user)) {
				throw new ApplicationException("User Not Found");
			}
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
		} catch (Exception e) {
			log.error("Error in updating user password {}", e.getMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private boolean isTokenFound(UserPasswordResetToken passToken) {
		return passToken != null;
	}

	private boolean isTokenExpired(UserPasswordResetToken passToken) {
		return passToken.getExpiryDate().before(Calendar.getInstance().getTime());
	}

	@Override
	public void checkEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new ApplicationException("Email Already Exists!");
		}
	}

	@Override
	public int sendTokenInEmail(String userEmail) {
		int i = 0;
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		String token = String.format("%06d", number);
		try {
			if (userRepository.existsByEmail(userEmail)) {
				throw new ApplicationException("Email Already Exists!");
			}
			
			emailService.createEmailVerificationTokenForUser(userEmail, token);
			i = emailService.sendEmailVerificationTemplate(userEmail, token);
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
		return i;
	}
}
