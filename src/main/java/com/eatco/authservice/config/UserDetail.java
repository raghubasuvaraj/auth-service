package com.eatco.authservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eatco.authservice.model.User;
import com.eatco.authservice.repository.UserRepository;

@Service
public class UserDetail implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = userRepository.findByEmail(username);

    if (user == null) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    return UserPrincipal.create(user);

//    return org.springframework.security.core.userdetails.User//
//        .withUsername(username)//
//        .roles("USER")
//        .password(user.getPassword())//
////      .authorities(user.getRoles())//
//        .accountExpired(false)//
//        .accountLocked(false)//
//        .credentialsExpired(false)//
//        .disabled(false)//
//        .build();
  }

}
