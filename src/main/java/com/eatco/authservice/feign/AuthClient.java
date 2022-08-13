package com.eatco.authservice.feign;
import org.springframework.cloud.openfeign.FeignClient;

import com.eatco.authservice.dto.RestResponse;
import com.eatco.authservice.dto.UserDto;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import net.minidev.json.JSONObject;

@FeignClient(name = "auth-service-call", url = "${authServiceURL}")
public interface AuthClient {

	@RequestLine("POST auth/signup")
	RestResponse createUser(UserDto userDto);
	
	@RequestLine("GET auth/email/check")
	RestResponse validateEmail(JSONObject json);

	@RequestLine("GET user/{userId}")
	@Headers({"Authorization: {token}", "Content-Type: application/json" })
	UserDto getUserInfo(@Param("userId")Long userId,@Param("token") String token);



	@RequestLine("DELETE user/email/{email}")
	@Headers({"Authorization: {token}", "Content-Type: application/json" })
	void revokeUserByEmailId(String email);
}