package com.eatco.authservice.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

	private Long id;
	
	private String name;
	
	private String userType;
	
	private boolean isActive;

	private String email;
	
	private String referralCode;
	
	private String mobileNumber;
	
	private Double latitude;
	
	private Double longitude;
	
	private String userLanguage;

	private Date dob;

	private Date dateOfAnniversary;

	private String city;
	
	private String country;
	
	private String zipCode;

}