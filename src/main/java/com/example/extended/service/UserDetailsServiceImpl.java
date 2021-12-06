package com.example.extended.service;

import com.example.extended.model.User;
import com.example.extended.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        if(user == null)
            user = userRepository.findUserByEmail(username);
        return UserDetailsImpl.build(user);
    }

}
