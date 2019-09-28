package com.brieffeed.backend.service;

import com.brieffeed.backend.domain.User;
import com.brieffeed.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User currentUser = userRepository.findByUserName(userName);
		return new org.springframework.security.core.userdetails.User(userName, currentUser.getPassword(), true, true,
				true, true, AuthorityUtils.createAuthorityList(currentUser.getRole().toString()));
	}
}
