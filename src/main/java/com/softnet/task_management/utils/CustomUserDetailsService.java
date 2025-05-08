package com.softnet.task_management.utils;

import com.softnet.task_management.domains.user.UserEntity;
import com.softnet.task_management.domains.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity user = userRepository
      .findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return new CustomUser(user);

  }

  public UserEntity getUserEntity(String username) throws UsernameNotFoundException{

    return userRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

  }


}
