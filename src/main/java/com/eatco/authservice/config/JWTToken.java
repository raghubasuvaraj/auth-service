package com.eatco.authservice.config;

import lombok.Data;

@Data
public class JWTToken {
	 private String token;
	    private Long userId;

	    public JWTToken(String token, Long userId) {
	        this.userId = userId;
	        this.token = token;
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }
}
