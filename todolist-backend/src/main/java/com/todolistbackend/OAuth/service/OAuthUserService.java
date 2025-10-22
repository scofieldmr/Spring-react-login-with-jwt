package com.todolistbackend.OAuth.service;

import com.todolistbackend.OAuth.dto.OAuthResponseDto;
import com.todolistbackend.OAuth.dto.OAuthUserDto;
import com.todolistbackend.dto.SignupResponse;

public interface OAuthUserService {

    SignupResponse createOrGetOAuthUser(OAuthUserDto oauthUserDto);
    OAuthResponseDto loginOrCreateOAuthUserReturnJwt(OAuthUserDto oauthUserDto);
}
