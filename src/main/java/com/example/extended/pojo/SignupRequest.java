package com.example.extended.pojo;

import lombok.Data;

import java.util.List;
@Data
public class SignupRequest {
    private String username;
    private String password;
    private List<String> roles;
}
