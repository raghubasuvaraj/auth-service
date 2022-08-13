package com.eatco.authservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.eatco.authservice.exception.CustomValidationException;
import com.eatco.authservice.exception.ErrorCode;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expiration}")
  private long validityInMilliseconds;

  @Autowired
  private UserDetail userDetail;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public JWTToken createToken(Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    return new JWTToken(Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(now)//
        .setIssuer(String.valueOf(userPrincipal.getUserId()))
        .setExpiration(validity)//
        .signWith(SignatureAlgorithm.HS256, secretKey)//
        .compact(),userPrincipal.getUserId());
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = this.userDetail.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) throws CustomValidationException {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new CustomValidationException(ErrorCode.EATCO_MANAGEMENT_1005);
    }
  }

}

