package com.eatco.authservice.service;

import java.text.ParseException;

import com.eatco.authservice.dto.*;
import com.eatco.authservice.exception.CustomValidationException;

public interface UserService {

	UserResponseDto getUserInfo(Long userId);

	MessageDto updateFCMToken(UserFCMTokenDto fcmTokenRequestDto) throws CustomValidationException;

	UserDto updateUser(UserProfileDto userDto) throws CustomValidationException, ParseException;

	MessageDto saveUserPermission(UserPermissionsDto userPermissionsDto) throws CustomValidationException;

	MessageDto updateUserMobile(UserProfileDto userDto) throws CustomValidationException;

	UserPermissionsDto fetchUserPermissions(Long userId) throws CustomValidationException;

	boolean deleteUserByEmail(String email) throws CustomValidationException;
}
