package com.todolistbackend.ResetPassword.service;

import com.todolistbackend.ResetPassword.dto.ResetPasswordDto;

public interface ResetPasswordService {

    String resetUserPassword(String email, ResetPasswordDto resetPasswordDto);

}
