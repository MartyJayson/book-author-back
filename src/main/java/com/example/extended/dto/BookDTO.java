package com.example.extended.dto;

import lombok.Data;

@Data
public class BookDTO {
    Long id;
    String title;
    String description;
    Long issueYear;
    Long authorid;
}
