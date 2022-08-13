package com.eatco.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatco.authservice.model.UserPasswordResetToken;

@Repository
public interface UserPasswordResetRepository extends JpaRepository<UserPasswordResetToken,Long> {

    UserPasswordResetToken findByToken(String token);

    UserPasswordResetToken findByUserId(Long userId);

    UserPasswordResetToken findByEmail(String userEmail);
}
