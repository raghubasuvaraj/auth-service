package com.eatco.authservice.dto;

public class BusinessAddressDTO {

	private Long id;

	private Long businessProfileId;

	private String address;

	private String postalCode;

	private String city;

	private Long countryId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBusinessProfileId() {
		return businessProfileId;
	}

	public void setBusinessProfileId(Long businessProfileId) {
		this.businessProfileId = businessProfileId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "BusinessAddressDTO [id=" + id + ", businessProfileId=" + businessProfileId + ", address=" + address
				+ ", postalCode=" + postalCode + ", city=" + city + ", countryId=" + countryId + "]";
	}
}
