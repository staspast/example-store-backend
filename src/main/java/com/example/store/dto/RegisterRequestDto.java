package com.example.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDto {

    private String username;
    private String password;
    private String passwordRepeat;
    private String firstName;
    private String lastName;
    private String email;
}
