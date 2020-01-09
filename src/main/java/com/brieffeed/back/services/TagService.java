package com.brieffeed.back.services;

import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.Tag;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.NotFoundException;
import com.brieffeed.back.repositories.TagRepository;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private UserRepository userRepository;

  public Iterable<Tag> findAll() {
    return tagRepository.findAll();
  }

  public Tag create(Tag tag, String username) {
    User user = userRepository.findByUserName(username);
    if (user.getRole().equals(Role.AUTHOR.getRole())) {
      return tagRepository.save(tag);
    } else {
      throw new NotFoundException("You do not have permission to create tag.");
    }
  }
}
