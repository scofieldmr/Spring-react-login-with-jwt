package com.todolistbackend.ResetPassword.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    @NotBlank(message = "Current Password Required")
    private String currentPassword;

    @NotBlank(message = "New Password Required")
    @Size(min = 4, max = 10, message = "Password size should be atleast 4 to 10")
    private String newPassword;

    @NotBlank(message = "Confirm New Password Required")
    @Size(min = 4, max = 10, message = "Password size should be atleast 4 to 10")
    private String confirmNewPassword;
}
