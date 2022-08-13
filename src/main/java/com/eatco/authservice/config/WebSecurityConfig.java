package com.eatco.authservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .anyRequest().authenticated();

    // If a user try to access a resource without having enough permissions
    http.exceptionHandling().accessDeniedPage("/login");

    // Apply JWT
    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // Allow swagger to be accessed without authentication
    web.ignoring().antMatchers("/v2/api-docs")//
        .antMatchers("/swagger-resources/**")//
        .antMatchers("/swagger-ui.html")//
        .antMatchers("/configuration/**")//
        .antMatchers("/webjars/**")//
        .antMatchers("/signup")
        .antMatchers("/login")
        .antMatchers("/auth/login")
        .antMatchers("/auth/signup")
        .antMatchers("/auth/send/token")
        .antMatchers("/auth/send/token/password")
        .antMatchers("/auth/verify/token")
        .antMatchers("/auth/update/password")
        .antMatchers("/auth/email/validate")
    	.antMatchers("/auth/email/check")
    	.antMatchers("/business/profile/create");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}

