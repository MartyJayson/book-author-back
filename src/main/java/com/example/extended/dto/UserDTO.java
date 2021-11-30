package com.example.extended.dto;

import com.example.extended.model.Role;
import com.sun.istack.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
