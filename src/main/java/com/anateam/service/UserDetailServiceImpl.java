package com.anateam.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anateam.entity.User;
import com.anateam.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(username).orElseThrow(
            ()
                -> new UsernameNotFoundException("User with phone number: \"" +
                                                 username + "\" not found."));

        return new org.springframework.security.core.userdetails.User(
            user.getPhoneNumber(), user.getPasswordHash(),
            List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
