package com.todolistbackend.service;

import com.todolistbackend.dto.*;
import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.exception.EmailAlreadyExistsException;
import com.todolistbackend.exception.IncorrectPasswordException;
import com.todolistbackend.forgetpassword.dto.MailBody;
import com.todolistbackend.forgetpassword.service.EmailService;
import com.todolistbackend.jwt.JwtUtils;
import com.todolistbackend.mapper.UserMapper;
import com.todolistbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    private final UserRepository userRepository;

    private  final UserMapper userMapper;

    private  final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final EmailService emailService;


    public UserServiceImp(UserRepository userRepository, UserMapper userMapper,
                          PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public SignupResponse createNewUser(SignupRequest signupRequest) {

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            logger.warn("Email already exists with this email address {}", signupRequest.getEmail());
            throw new EmailAlreadyExistsException("Email already exists :" + signupRequest.getEmail());
        }

        MyUsers newUser = userMapper.signupRequestToUser(signupRequest);
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        MyUsers savedUser = userRepository.save(newUser);

        logger.info("New user created : " + savedUser.getEmail());

        MailBody mailBody = MailBody.builder()
                .mailTo(savedUser.getEmail())
                .subject("Todo Application - User Registration")
                .body("User Registered Successfully with the user role - "+ savedUser.getRole())
                .build();

//        emailService.sendEmail(mailBody);

        logger.info("Register Successful Email sent to the Registered User : " + savedUser.getEmail());

        return userMapper.userToSignupResponse(savedUser);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {

        if(!userRepository.existsByEmail(loginRequest.getUsername())){
            logger.warn("User not found with the username :" + loginRequest.getUsername());
            throw new UsernameNotFoundException("Username not found with the username : " + loginRequest.getUsername());
        }

        MyUsers loggedUser = userRepository.findByEmail(loginRequest.getUsername());

        String loggedPassword = passwordEncoder.encode(loginRequest.getPassword());

        if(!loggedPassword.equals(loggedUser.getPassword())){
            logger.warn("Incorrect password , Kindly check your password");
            throw new IncorrectPasswordException("Incorrect password , Kindly please try again..");
        }

        LoginResponse loginResponse = userMapper.userToLoginResponse(loggedUser);

        logger.info("User logged in : " + loggedUser.getEmail());

        return loginResponse;
    }

    @Override
    public LoginJwtResponse loginUserJwt(LoginRequest loginRequest) {

        if(!userRepository.existsByEmail(loginRequest.getUsername())){
            logger.warn("User not found with the username :" + loginRequest.getUsername());
            throw new UsernameNotFoundException("Username not found with the username : " + loginRequest.getUsername());
        }

        MyUsers loggedUser = userRepository.findByEmail(loginRequest.getUsername());

        String loggedPassword = passwordEncoder.encode(loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate
                       (new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtils.generateJwtToken(authentication);

        String username = jwtUtils.getUsernameFromJwtToken(jwtToken);
        String roles = jwtUtils.getRoleFromJwtToken(jwtToken);

        logger.info("JWT token : " + jwtToken);
        logger.info("User logged in : " + username);
        logger.info("User role : " + roles);

        LoginJwtResponse loginJwtResponse = new LoginJwtResponse();
        loginJwtResponse.setUsername(username);
        loginJwtResponse.setRole(roles);
        loginJwtResponse.setTokenType("Bearer");
        loginJwtResponse.setToken(jwtToken);

        return loginJwtResponse;
    }

    @Override
    public ProfileDetailsDto getUserDetails(String username) {

        MyUsers user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found with the username : " + username);
        }

        ProfileDetailsDto profileDetailsDto = userMapper.userToProfileDetailsDto(user);

        logger.info("User details : " + profileDetailsDto);
        return profileDetailsDto;
    }
}
