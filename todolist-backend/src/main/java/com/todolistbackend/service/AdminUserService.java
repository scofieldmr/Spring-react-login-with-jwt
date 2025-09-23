package com.todolistbackend.service;

import com.todolistbackend.dto.MyUserDetailsDto;

import java.util.List;

public interface AdminUserService {

    List<MyUserDetailsDto> findAllUserDetails();

    MyUserDetailsDto findUserDetailsByEmail(String email);

    List<MyUserDetailsDto> findAllUserDetailsByRole(String role);

}
