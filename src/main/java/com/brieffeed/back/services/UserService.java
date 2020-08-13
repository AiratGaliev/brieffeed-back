package com.brieffeed.back.services;

import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.UsernameAlreadyExistsException;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserService(
      UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User saveUser(User user) {
    try {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      user.setUserName(user.getUsername());
      user.setConfirmPassword("");
      return userRepository.save(user);
    } catch (Exception e) {
      throw new UsernameAlreadyExistsException(
          "Username '" + user.getUsername() + "' already exists");
    }
  }

}