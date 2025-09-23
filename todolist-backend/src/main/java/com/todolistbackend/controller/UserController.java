package com.todolistbackend.controller;

import com.todolistbackend.dto.*;
import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.service.UserService;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Validated SignupRequest signupRequest) {
        logger.info("New user signing in: " + signupRequest.getEmail());

        SignupResponse newUserSignUp = userService.createNewUser(signupRequest);

        return new ResponseEntity<>(newUserSignUp, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest) {
        logger.info("Logging in user: " + loginRequest.getUsername());

        LoginResponse loginResponse = userService.loginUser(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/jwtlogin")
    public ResponseEntity<?> jwtLogin(@RequestBody @Validated LoginRequest loginRequest) {
        logger.info("Logging in user with JWT: " + loginRequest.getUsername());

        LoginJwtResponse loginJwtResponse = userService.loginUserJwt(loginRequest);
        return new ResponseEntity<>(loginJwtResponse, HttpStatus.OK);
    }

    @GetMapping("/profileDetails")
    public ResponseEntity<?> getProfileDetails(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Logged in user: " + userDetails.getUsername());

        ProfileDetailsDto profileDetailsDto = userService.getUserDetails(userDetails.getUsername());
        return new ResponseEntity<>(profileDetailsDto, HttpStatus.OK);
    }

}
