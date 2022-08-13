package com.eatco.authservice.dto;

import lombok.Data;

@Data
public class UserFCMTokenDto {

    private Long id;
    private Long userId;
    private String fcmToken;
    private String deviceId;
	@Override
	public String toString() {
		return "UserFCMTokenDto [id=" + id + ", userId=" + userId + ", fcmToken=" + fcmToken + ", deviceId=" + deviceId
				+ "]";
	}

}
