package com.example.extended.controller;

import com.example.extended.dto.AuthorDTO;
import com.example.extended.model.Author;
import com.example.extended.pojo.SimplePage;
import com.example.extended.repository.AuthorRepository;
import com.example.extended.service.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    EntityManager entityManager;
    @GetMapping("/authors")
    public ResponseEntity<?> getAllAuthors(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(entityManager.getAllAuthors(pageable));
    }

    @GetMapping("/date")
    public ResponseEntity<?> getAllAuthorsByDate(Pageable pageable, @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @DateTimeFormat(pattern="yyyy-MM-dd") Date end){
        return ResponseEntity.ok(entityManager.getAuthorByDateBetween(pageable, start, end));
    }
    @GetMapping("/test")
    public ResponseEntity<?> getTest(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(entityManager.getAuthorAndCount(pageable));
    }

    @GetMapping("/testdate")
    public ResponseEntity<?> getTest(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, @DateTimeFormat(pattern="yyyy-MM-dd") Date start, @DateTimeFormat(pattern="yyyy-MM-dd") Date end){
        return ResponseEntity.ok(entityManager.countBookByAuthorAndYear(pageable, start , end));
    }

}
