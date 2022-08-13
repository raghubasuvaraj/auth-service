package com.eatco.authservice.service.impl;

import java.text.ParseException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.eatco.authservice.dto.MessageDto;
import com.eatco.authservice.dto.UserDto;
import com.eatco.authservice.dto.UserFCMTokenDto;
import com.eatco.authservice.dto.UserPermissionsDto;
import com.eatco.authservice.dto.UserProfileDto;
import com.eatco.authservice.dto.UserResponseDto;
import com.eatco.authservice.enums.AuthConstants;
import com.eatco.authservice.exception.ApplicationException;
import com.eatco.authservice.exception.CustomValidationException;
import com.eatco.authservice.exception.ErrorCode;
import com.eatco.authservice.model.User;
import com.eatco.authservice.model.UserFCMToken;
import com.eatco.authservice.model.UserPermissions;
import com.eatco.authservice.repository.UserFCMTokenRepository;
import com.eatco.authservice.repository.UserPermissionRepository;
import com.eatco.authservice.repository.UserRepository;
import com.eatco.authservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ModelMapper modelmapper;
	private final UserFCMTokenRepository userFCMTokenRepository;
	private final UserPermissionRepository userPermissionRepository;

	@Override
	public UserResponseDto getUserInfo(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new ApplicationException("User doesn't exist!");
		}
		return modelmapper.map(userOptional.get(), UserResponseDto.class);
	}

	@Override
	public MessageDto updateFCMToken(UserFCMTokenDto userFCMTokenDto) throws CustomValidationException {
		try {
			Optional<User> user = userRepository.findById(userFCMTokenDto.getUserId());
			if (user.isPresent()) {
				UserFCMToken userFCMToken = modelmapper.map(userFCMTokenDto, UserFCMToken.class);
				userFCMTokenRepository.save(userFCMToken);
			}
			return new MessageDto(AuthConstants.SUCCESS.getValue(), AuthConstants.FCM_TOKEN_SUCCESS.getValue());
		} catch (Exception e) {
			log.error("Error in updating user fcm token {}", e.getMessage());
			throw new CustomValidationException(ErrorCode.EATCO_MANAGEMENT_1013);
		}
	}

	@Override
	public UserDto updateUser(UserProfileDto userProfileDto) throws CustomValidationException, ParseException {
		Optional<User> userOptional = userRepository.findById(userProfileDto.getUserId());
		if (!userOptional.isPresent()) {
			throw new CustomValidationException(ErrorCode.EATCO_MANAGEMENT_1001);
		}
		User user = userOptional.get();
		user.setLatitude(userProfileDto.getLatitude());
		user.setLongitude(userProfileDto.getLongitude());
		user.setCity(userProfileDto.getCity());
		user.setMobileNumber(userProfileDto.getMobileNumber());
		userRepository.save(user);
		return modelmapper.map(user, UserDto.class);
	}

	@Override
	public MessageDto updateUserMobile(UserProfileDto userDto) throws CustomValidationException {
		try {
			Optional<User> userOptional = userRepository.findById(userDto.getUserId());
			if (!userOptional.isPresent()) {
				throw new CustomValidationException(ErrorCode.EATCO_MANAGEMENT_1001);
			}
			User user = userOptional.get();
			user.setMobileNumber(userDto.getMobileNumber());
			userRepository.save(user);
			return new MessageDto(AuthConstants.SUCCESS.getValue(), AuthConstants.UPDATE_SUCCESS.getValue());
		} catch (Exception e) {
			log.error("Error in updating user mobile {}", e.getMessage());
			throw new CustomValidationException(ErrorCode.EATCO_MANAGEMENT_1001);
		}
	}

    @Override
    public MessageDto saveUserPermission(UserPermissionsDto userPermissionsDto) throws CustomValidationException {
        try {
            Optional<User> userOptional = userRepository.findById(userPermissionsDto.getUserId());
            if(!userOptional.isPresent()) {
                throw new CustomValidationException(ErrorCode.EATCO_MANAGEMENT_1008);
            }
            Optional<UserPermissions> userPermissionsOptional =
                    userPermissionRepository.findByUserId(userPermissionsDto.getUserId());
            UserPermissions userPermissions = modelmapper.map(userPermissionsDto,UserPermissions.class);
            if(userPermissionsOptional.isPresent()) {
                userPermissions.setId(userPermissionsOptional.get().getId());
            }
            userPermissionRepository.save(userPermissions);
            return new MessageDto(AuthConstants.SUCCESS.getValue(),AuthConstants.SAVE_PERMISSIONS_SUCCESS.getValue());
        } catch (Exception e) {
            log.error("Error in saving user permission {}",e.getMessage());
            throw new CustomValidationException(ErrorCode.EATCO_MANAGEMENT_1014);
        }
    }

	@Override
	public UserPermissionsDto fetchUserPermissions(Long userId) throws CustomValidationException {
		UserPermissionsDto userPermissionsDto = new UserPermissionsDto();
		try {
			Optional<UserPermissions> userOptional = userPermissionRepository.findByUserId(userId);
			userPermissionsDto.setUserId(userId);
			if (userOptional.isPresent()) {
				userPermissionsDto.setIsBankingEnabled(userOptional.get().isBankingEnabled());
				userPermissionsDto.setIsBillingEnabled(userOptional.get().isBillingEnabled());
				userPermissionsDto.setIsEmailEnabled(userOptional.get().isEmailEnabled());
				userPermissionsDto.setIsPushEnabled(userOptional.get().isPushEnabled());
				userPermissionsDto.setIsMerchantUpdatesEnabled(userOptional.get().isMerchantUpdatesEnabled());
				userPermissionsDto.setIsSmsEnabled(userOptional.get().isSmsEnabled());
				return userPermissionsDto;
			}
		} catch (Exception e) {
			log.error("Error in fetching user permission {}", e.getMessage());
			throw new CustomValidationException(ErrorCode.EATCO_MANAGEMENT_1015);
		}
		return userPermissionsDto;
	}

	@Override
	public boolean deleteUserByEmail(String email) throws CustomValidationException {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			userRepository.deleteById(user.getId());
			return true;
		}
		return false;
	}
}
