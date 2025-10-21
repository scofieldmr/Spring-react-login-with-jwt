package com.todolistbackend.service;

import com.todolistbackend.OAuth.dto.OAuthResponseDto;
import com.todolistbackend.OAuth.dto.OAuthUserDto;
import com.todolistbackend.dto.SignupResponse;
import com.todolistbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OAuthServiceImp implements OAuthUserService{

    @Value("${oauth.google.client-id}")
    private String googleClientId;

    private final UserRepository userRepository;

    public OAuthServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public SignupResponse createOrGetOAuthUser(OAuthUserDto oauthUserDto) {
        return null;
    }

    @Override
    public OAuthResponseDto loginOrCreateOAuthUserReturnJwt(OAuthUserDto oauthUserDto) {
        return null;
    }
}
