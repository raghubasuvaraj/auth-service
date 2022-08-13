package com.eatco.authservice.dto;

import lombok.Data;

@Data
public class UserProfileDto {

    private Long userId;
    private String dob;
    private String dateOfAnniversary;
    private String mobileNumber;
    private String city;
    private Double latitude;
    private Double longitude;

}
