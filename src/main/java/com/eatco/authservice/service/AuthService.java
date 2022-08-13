package com.eatco.authservice.service;

import com.eatco.authservice.config.JWTToken;
import com.eatco.authservice.dto.UserDto;
import com.eatco.authservice.model.User;

public interface AuthService {

    User signUp(UserDto userDto);

    JWTToken login(UserDto userDto);
    
    void validateEmail(String email);

    int sendTokenForResetPassword(String userEmail);
    
    int sendTokenInEmail(String userEmail);

    void verifyToken(String token);

    void updatePassword(String email, String password);
    
    void checkEmail(String email);

}
