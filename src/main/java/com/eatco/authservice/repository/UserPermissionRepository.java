package com.eatco.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatco.authservice.model.User;
import com.eatco.authservice.model.UserPermissions;

import java.util.Optional;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermissions,Long> {


    Optional<UserPermissions> findByUserId(Long userId);
}