package com.eatco.authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessProfileDTO {

	private Long id;
	private Long userId;
	private Long businessId;
	private String businessName;
	private String registerationNumber;
	private String companyName;
	private String companyDescription;
	private Long categoryId;
	private String categoryName;
	private String cashbackImageUrl;
	private String logoUrl;
	private Double rating;
	private String presignedLogoUrl;
	private String contactPerson;
	private String email;
	private String phoneNumber;
	private String website;
	private String password;
	private Double latitude;
	private Double longitude;
	private String userLanguage;
	private String countryCode;
	private Double distance;
	
	public String toString() {
		return "BusinessProfileDTO [id=" + id + ", userId=" + userId + ", businessName=" + businessName
				+ ", registerationNumber=" + registerationNumber + ", companyName=" + companyName
				+ ", companyDescription=" + companyDescription + ", categoryId=" + categoryId + ", logoUrl=" + logoUrl
				+ ", contactPerson=" + contactPerson + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", website=" + website + ", password=" + password + ", latitude=" + latitude + ", longitude="
				+ longitude + ", userLanguage=" + userLanguage + "]";
	}

}
