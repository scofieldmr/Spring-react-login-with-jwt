package com.todolistbackend.service;

import com.todolistbackend.entity.CustomUserDetails;
import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUsers loadedUser = userRepository.findByEmail(username);
        if (loadedUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new CustomUserDetails(loadedUser.getEmail(),
                loadedUser.getPassword(), List.of(new SimpleGrantedAuthority(loadedUser.getRole().name())));
    }
}
