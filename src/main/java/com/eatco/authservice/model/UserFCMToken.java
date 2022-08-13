package com.eatco.authservice.model;

import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "user_fcm_token")
@Data
public class UserFCMToken extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;
    private Long userId;
    private String deviceId;
    private String fcmToken;

}