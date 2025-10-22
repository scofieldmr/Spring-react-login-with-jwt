package com.todolistbackend.OAuth.service;

import com.todolistbackend.OAuth.dto.OAuthResponseDto;
import com.todolistbackend.OAuth.dto.OAuthUserDto;
import com.todolistbackend.dto.SignupResponse;
import com.todolistbackend.entity.ERoles;
import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.exception.EmailAlreadyExistsException;
import com.todolistbackend.forgetpassword.dto.MailBody;
import com.todolistbackend.forgetpassword.service.EmailService;
import com.todolistbackend.jwt.JwtUtils;
import com.todolistbackend.mapper.UserMapper;
import com.todolistbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OAuthServiceImp implements OAuthUserService{

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
