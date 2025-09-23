package com.todolistbackend.controller;

import com.todolistbackend.forgetpassword.dto.ChangePasswordDto;
import com.todolistbackend.forgetpassword.service.ForgotPasswordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forgotPassword")
@CrossOrigin("*")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;


    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/email-verify/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable("email") String email) {
       String verifiedEmail = forgotPasswordService.verifyEmail(email);
       return new ResponseEntity<>(verifiedEmail, HttpStatus.OK);
    }

    @PostMapping("/otp-verify/{email}/{otp}")
    public ResponseEntity<String> verifyOtp(@PathVariable("email") String email, @PathVariable("otp") Long otp) {
        String verifiedOTP = forgotPasswordService.verifyOtp(email,otp);
        return new ResponseEntity<>(verifiedOTP, HttpStatus.OK);
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@PathVariable("email") String email,
                                             @Valid @RequestBody ChangePasswordDto changePasswordDto) {
        String passwordChanged = forgotPasswordService.passwordChange(email,changePasswordDto);
        return new ResponseEntity<>(passwordChanged, HttpStatus.OK);
    }
}
