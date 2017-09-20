package com.example.store.dto;

import com.example.store.model.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private String username;
    private String password;
    private List<Role> roles;
}
