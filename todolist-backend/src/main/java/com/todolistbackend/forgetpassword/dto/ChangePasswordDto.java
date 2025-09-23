package com.todolistbackend.forgetpassword.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChangePasswordDto {

    @NotBlank(message = "Password Required!")
    private String newPassword;

    @NotBlank(message = "Repeat Password Required!")
    private String repeatNewPassword;
}
