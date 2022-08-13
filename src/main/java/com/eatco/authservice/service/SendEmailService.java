package com.eatco.authservice.service;

import com.eatco.authservice.model.User;

public interface SendEmailService {

    void createPasswordResetTokenForUser(User user, String token);
    
    void createEmailVerificationTokenForUser(String userEmail, String token);
    
    int constructResetTokenEmail(String token, User user);
    
    int sendEmailVerificationTemplate(String email, String token);
    
    int sendWelcomeEmail(User user);
}
