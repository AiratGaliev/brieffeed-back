package com.brieffeed.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brieffeed.back.domain.User;
import com.brieffeed.back.repositories.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User currentUser = userRepository.findByUserName(userName);
        if (currentUser == null)
            new UsernameNotFoundException("User not found");
        return currentUser;
    }

    @Transactional
    public UserDetails loadUserById(Long userName) {
        User currentUser = userRepository.findByUserName(userName.toString());
        if (currentUser == null)
            new UsernameNotFoundException("User not found");
        return currentUser;
    }
}
