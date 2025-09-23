package com.todolistbackend.service;

import com.todolistbackend.dto.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface UserService {

    SignupResponse createNewUser(SignupRequest signupRequest);

    LoginResponse loginUser(LoginRequest loginRequest);

    LoginJwtResponse loginUserJwt(LoginRequest loginRequest);

    ProfileDetailsDto getUserDetails(String username);

}
