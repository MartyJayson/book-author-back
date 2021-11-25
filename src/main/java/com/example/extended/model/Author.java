package com.example.extended.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String name;
        Date birthdate;
    @OneToMany(mappedBy = "author")
    List<Book> bookList = new ArrayList<>();

}
