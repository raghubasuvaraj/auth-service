package com.eatco.authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupDTO {

	private BusinessProfileDTO businessProfile;
	private BusinessAddressDTO businessAddress;

}
