package com.eatco.authservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends AuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String userType;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "referral_code", length = 200)
    private String referralCode;
    
    @Column(name = "cvr" , nullable = true, length = 200)
    private String cvrCode;

    @Column(name = "mobile_number",length = 100)
    private String mobileNumber;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "user_language")
    private String userLanguage;

    @Column(name = "city",length = 200)
    private String city;
    
    @Column(name = "country", length = 200)
    private String country;
    
    @Column(name = "zip_code" , length = 20)
    private String zipCode;

}

