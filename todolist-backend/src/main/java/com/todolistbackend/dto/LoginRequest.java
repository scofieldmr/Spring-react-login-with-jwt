package com.todolistbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {

    @NotBlank(message = "Username Required")
    private String username;

    @NotBlank(message = "Password Required ")
    private String password;
}
