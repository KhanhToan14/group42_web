 package com.web.recruitment.securiry.services;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.core.userdetails.UserDetailsService;
 import
 org.springframework.security.core.userdetails.UsernameNotFoundException;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;

 import com.web.recruitment.persistence.dto.User;
 import com.web.recruitment.persistence.mapper.UserMapper;

 @Service
 public class UserDetailsServiceImpl implements UserDetailsService {
 @Autowired
 UserMapper userMapper;

 @Override
 @Transactional
 public UserDetails loadUserByUsername(String username) throws
 UsernameNotFoundException {
 User user = userMapper.findByUsername(username)
 .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

 return UserDetailsImpl.build(user);
 }

 }