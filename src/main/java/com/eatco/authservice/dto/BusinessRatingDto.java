package com.eatco.authservice.dto;

import lombok.Data;

@Data
public class BusinessRatingDto {

    private Long userId;
    private Long businessProfileId;
    private Double rating;
}
