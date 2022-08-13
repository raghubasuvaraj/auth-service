package com.eatco.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eatco.authservice.model.UserFCMToken;

import java.util.List;
import java.util.Set;

@Repository
public interface UserFCMTokenRepository extends JpaRepository<UserFCMToken,Long> {

    @Query("select tokens from UserFCMToken tokens where userId in :userList")
    List<UserFCMToken> selectAllUserTokens(@Param("userList") Set<Long> userList);
}
