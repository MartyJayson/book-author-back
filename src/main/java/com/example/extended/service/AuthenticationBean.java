package com.example.extended.service;

import lombok.Data;

@Data
public class AuthenticationBean {
    private String message;

    public AuthenticationBean(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("HelloWorldBean [message=%s]",message);
    }
}
