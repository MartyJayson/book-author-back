package com.example.extended.pojo;

import com.example.extended.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private Long id;
    private String username;
    private List<Role> roles;

    public JwtResponse(String token, Long id, String username, List roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
