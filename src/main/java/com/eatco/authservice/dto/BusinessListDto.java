package com.eatco.authservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessListDto {

    private Long userId;
    private String categoryName;
    private String imageUrl;
    private Long businessId;
    private String city;
    private String address;
    private String postalCode;
    private String categoryImageUrl;
    private String cashbackType;
    private BigDecimal firstPurchase;
    private BigDecimal secondPurchase;
    private Double latitude;
    private Double longitude;
    private String businessName;
    private Double rating;
    private String logoUrl;
    private String distance;
    private BigDecimal mimQualifyAmount;

}
