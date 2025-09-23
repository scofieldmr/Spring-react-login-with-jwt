package com.todolistbackend.forgetpassword.service;

import com.todolistbackend.forgetpassword.dto.ChangePasswordDto;

public interface ForgotPasswordService {

    String verifyEmail(String email);

    String verifyOtp(String email,Long otp);

    String passwordChange(String email, ChangePasswordDto changePasswordDto);

}
