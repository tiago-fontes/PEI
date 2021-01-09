package com.peiload.ridecare.login.service;

import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return new MPUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("Username not found.");
        }
    }

    public User retrieveLoginUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Username not found.");
        }
    }
}