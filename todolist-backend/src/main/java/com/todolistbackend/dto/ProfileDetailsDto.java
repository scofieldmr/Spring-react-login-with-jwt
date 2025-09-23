package com.todolistbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetailsDto {

    private String profileName;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
