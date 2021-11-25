package com.example.extended.controller;

import com.example.extended.model.Author;
import com.example.extended.model.Book;
import com.example.extended.service.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HelloController {
    @Autowired
    EntityManager entityManager;
    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }
    @GetMapping("/calc")
    public ResponseEntity<?> calc(int a, int b){
        return ResponseEntity.ok(entityManager.sum(a, b));
    }
    @GetMapping("/date")
    public ResponseEntity<?> date(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date date){
        Date date1 = entityManager.date(date);
        return ResponseEntity.ok(date1.toString());
    }
    @GetMapping("/test")
    public ResponseEntity<?> getAllB(){
        return ResponseEntity.ok(entityManager.getAllB());
    }
    @GetMapping("/testA")
    public ResponseEntity<?> getAllA(){
        return ResponseEntity.ok(entityManager.getAllA());
    }
    @PostMapping("/createbook")
    public ResponseEntity<?> createBook(@RequestBody Book book){
        return ResponseEntity.ok(entityManager.createBook(book));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editBook(@PathVariable Long id, @RequestBody Book book){
        return ResponseEntity.ok(entityManager.updateBook(book, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        return ResponseEntity.ok(entityManager.deleteBook(id));
    }
    @PostMapping("/createAuthor")
    public ResponseEntity<?> createAuthor(@RequestBody Author author){
        return ResponseEntity.ok(entityManager.createAuthor(author));
    }
    @PutMapping("/a/{id}")
    public ResponseEntity<?> editAuthor(@PathVariable Long id, @RequestBody Author author){
        return ResponseEntity.ok(entityManager.editAuthor(author, id));
    }
    @DeleteMapping("/a/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id){
        return ResponseEntity.ok(entityManager.deleteAuthor(id));
    }
}
