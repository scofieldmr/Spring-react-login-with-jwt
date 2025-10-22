package com.todolistbackend.service;

import com.todolistbackend.dto.*;

public interface UserService {

    SignupResponse createNewUser(SignupRequest signupRequest);

    LoginResponse loginUser(LoginRequest loginRequest);

    LoginJwtResponse loginUserJwt(LoginRequest loginRequest);

    ProfileDetailsDto getUserDetails(String username);

}
