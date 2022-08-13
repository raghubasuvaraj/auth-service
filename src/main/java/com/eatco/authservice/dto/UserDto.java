package com.eatco.authservice.dto;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {

	private Long id;
	
	@NotEmpty(message = "Name cannot be empty")
	private String name;
	
	@NotEmpty(message = "User Type cannot be empty")
	private String userType;
	
	private boolean isActive;

	@NotEmpty(message = "Password cannot be empty")
	private String password;
	
	@NotEmpty(message = "Email cannot be empty")
	private String email;
	
	private String referralCode;
	
	private String mobileNumber;

	private Double latitude;

	private Double longitude;

	private String userLanguage;

	private Date dob;

	private Date dateOfAnniversary;

	@NotEmpty(message = "City cannot be empty")
	private String city;
	
	@NotEmpty(message = "Country cannot be empty")
	private String country;
	
	@NotEmpty(message = "Zip Code cannot be empty")
	private String zipCode;

}