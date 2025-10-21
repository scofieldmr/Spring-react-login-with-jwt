package com.todolistbackend.OAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String provider;
    private String providerId;
}
