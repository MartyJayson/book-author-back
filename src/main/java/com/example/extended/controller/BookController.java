package com.example.extended.controller;

import com.example.extended.repository.BookRepository;
import com.example.extended.service.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/book")
public class BookController {

    @Autowired
    EntityManager entityManager;

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(entityManager.getAllBooks(pageable));
    }
    @GetMapping("")
    public ResponseEntity<?> getAllBooksByName(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, String title){
        return ResponseEntity.ok(entityManager.getAllBooksByName(pageable,title));
    }
    @GetMapping("/date")
    public ResponseEntity<?> getAllBooksByNameAndDate(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, String title, Long year){
        return ResponseEntity.ok(entityManager.getAllBooksByNameAndDate(pageable, title, year));
    }

}
