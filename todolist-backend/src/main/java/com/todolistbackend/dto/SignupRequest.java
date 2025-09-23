package com.todolistbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupRequest {
    @NotBlank(message = "First Name Required! ")
    private String firstName;

    @NotBlank(message = "Last Name Required! ")
    private String lastName;

    @NotBlank(message = "Email Required! ")
    private String email;

    @NotBlank(message = "Password Required! ")
    private String password;

    @NotBlank(message = "Confirm Password Required! ")
    private String confirmPassword;

    @NotBlank(message = "Role Required! ")
    private String role;
}
