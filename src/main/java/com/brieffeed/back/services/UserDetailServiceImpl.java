package com.brieffeed.back.services;

import com.brieffeed.back.domain.User;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User currentUser = userRepository.findByUserName(username);
    if (currentUser == null) {
      new UsernameNotFoundException("User not found");
    }
    return currentUser;
  }

  @Transactional
  public User loadUserById(Long id) {
    User currentUser = userRepository.getById(id);
    if (currentUser == null) {
      new UsernameNotFoundException("User not found");
    }
    return currentUser;
  }
}
