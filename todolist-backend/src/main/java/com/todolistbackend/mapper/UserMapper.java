package com.todolistbackend.mapper;

import com.todolistbackend.dto.*;
import com.todolistbackend.entity.ERoles;
import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.exception.PasswordMismatchException;
import com.todolistbackend.exception.UserRoleNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static MyUsers signupRequestToUser(SignupRequest signupRequest) {
        MyUsers myUsers = new MyUsers();
        myUsers.setFirstName(signupRequest.getFirstName());
        myUsers.setLastName(signupRequest.getLastName());
        myUsers.setEmail(signupRequest.getEmail());

        if(!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match. Please try again.");
        }

        if(signupRequest.getRole().equalsIgnoreCase("admin")) {
            myUsers.setRole(ERoles.ROLE_ADMIN);
        }
        else if(signupRequest.getRole().equalsIgnoreCase("moderator")) {
            myUsers.setRole(ERoles.ROLE_MODERATOR);
        }
        else if(signupRequest.getRole().equalsIgnoreCase("user")) {
            myUsers.setRole(ERoles.ROLE_USER);
        }
        else{
            throw new UserRoleNotFoundException("Invalid role ,"+ signupRequest.getRole()+ "Kindly select required role");
        }

        return myUsers;
    }

    public static SignupResponse userToSignupResponse(MyUsers myUsers) {
        SignupResponse signupResponse = new SignupResponse();
        signupResponse.setEmail(myUsers.getEmail());
        signupResponse.setRole(myUsers.getRole().toString());
        signupResponse.setMessage("User Successfully registered with the role " + myUsers.getRole());

        return signupResponse;
    }

    public static LoginResponse userToLoginResponse(MyUsers myUsers) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(myUsers.getEmail());
        loginResponse.setRole(myUsers.getRole().toString());
        loginResponse.setMessage("User Successfully logged in.");
        return loginResponse;
    }

    public static ProfileDetailsDto userToProfileDetailsDto(MyUsers myUsers) {
        ProfileDetailsDto profileDetailsDto = new ProfileDetailsDto();
        profileDetailsDto.setProfileName(myUsers.getEmail());
        profileDetailsDto.setFirstName(myUsers.getFirstName());
        profileDetailsDto.setLastName(myUsers.getLastName());
        profileDetailsDto.setEmail(myUsers.getEmail());
        profileDetailsDto.setRole(myUsers.getRole().toString());
        return profileDetailsDto;
    }

    public static MyUserDetailsDto userToMyUserDetailsDto(MyUsers myUsers) {
        MyUserDetailsDto myUserDetailsDto = new MyUserDetailsDto();
        myUserDetailsDto.setProfileName(myUsers.getEmail());
        myUserDetailsDto.setFirstName(myUsers.getFirstName());
        myUserDetailsDto.setLastName(myUsers.getLastName());
        myUserDetailsDto.setEmail(myUsers.getEmail());
        myUserDetailsDto.setRole(myUsers.getRole().toString());
        return myUserDetailsDto;
    }

}
