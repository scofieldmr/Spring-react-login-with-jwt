package com.todolistbackend.service;

import com.todolistbackend.dto.MyUserDetailsDto;
import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.forgetpassword.exception.UserEmailNotFoundException;
import com.todolistbackend.mapper.UserMapper;
import com.todolistbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserServiceImp implements AdminUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public AdminUserServiceImp(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<MyUserDetailsDto> findAllUserDetails() {

        List<MyUsers> findAllUsers = userRepository.findAll();
        List<MyUserDetailsDto> myUserDetailsDtoList = new ArrayList<>();
        for (MyUsers user : findAllUsers) {
            MyUserDetailsDto myUserDetailsDto = userMapper.userToMyUserDetailsDto(user);
            myUserDetailsDtoList.add(myUserDetailsDto);
        }
        return myUserDetailsDtoList;
    }

    @Override
    public MyUserDetailsDto findUserDetailsByEmail(String email) {

        MyUsers findUserByEmail = userRepository.findByEmail(email);
        if(findUserByEmail==null) {
            throw new UserEmailNotFoundException("User with email not found with email: " + email);
        }
        return userMapper.userToMyUserDetailsDto(findUserByEmail);
    }

    @Override
    public List<MyUserDetailsDto> findAllUserDetailsByRole(String role) {

        List<MyUsers> findAllUsersByRole = userRepository.findAllByRole(role);
        List<MyUserDetailsDto> myUserDetailsDtoList = new ArrayList<>();
        for (MyUsers user : findAllUsersByRole) {
            MyUserDetailsDto myUserDetailsDto = userMapper.userToMyUserDetailsDto(user);
            myUserDetailsDtoList.add(myUserDetailsDto);
        }
        return myUserDetailsDtoList;
    }
}
