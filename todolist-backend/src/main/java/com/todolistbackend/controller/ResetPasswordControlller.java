package com.todolistbackend.controller;

import com.todolistbackend.ResetPassword.dto.ResetPasswordDto;
import com.todolistbackend.ResetPassword.service.ResetPasswordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resetPassword")
@CrossOrigin("*")
public class ResetPasswordControlller {

    private final ResetPasswordService resetPasswordService;

    public ResetPasswordControlller(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping("/")
    public ResponseEntity<?> resetPassword(@AuthenticationPrincipal UserDetails userDetails,
                                           @Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        String resetPassword = resetPasswordService.resetUserPassword(userDetails.getUsername(), resetPasswordDto);
        return new ResponseEntity<>(resetPassword, HttpStatus.OK);
    }

}
