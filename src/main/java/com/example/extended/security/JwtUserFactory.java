package com.example.extended.security;

import com.example.extended.model.Role;
import com.example.extended.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory(){

    }
    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(role -> {
                    return new SimpleGrantedAuthority(role.getName());
                }).collect(Collectors.toList());
    }
}
