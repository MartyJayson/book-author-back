package com.example.extended.pojo;

import lombok.Data;

import java.util.List;
@Data
public class FindRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
}
