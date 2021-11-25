package com.example.extended.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorDTO {
    Long id;
    String name;
    Date birthdate;
}
