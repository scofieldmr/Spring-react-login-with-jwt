package com.todolistbackend.controller;

import com.todolistbackend.dto.MyUserDetailsDto;
import com.todolistbackend.service.AdminUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminUserService adminUserService;

    public AdminController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping("/allUser")
    public ResponseEntity<?> findAllUsers(){
        List<MyUserDetailsDto> myUserDetailsDtoList = adminUserService.findAllUserDetails();
        if(myUserDetailsDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(myUserDetailsDtoList, HttpStatus.OK);
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable("email") String email){
        MyUserDetailsDto getUserDetailByEmail = adminUserService.findUserDetailsByEmail(email);
        return new ResponseEntity<>(getUserDetailByEmail, HttpStatus.OK);
    }

    @GetMapping("/getUsersByRole/{role}")
    public ResponseEntity<?> findUsersByRole(@PathVariable("role") String role){
        List<MyUserDetailsDto> userDetailsByRoleList = adminUserService.findAllUserDetailsByRole(role);
        if(userDetailsByRoleList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userDetailsByRoleList, HttpStatus.OK);
    }
}
