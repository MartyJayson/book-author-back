package com.example.extended.repository;

import com.example.extended.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page <Author> findAll(Pageable pageable);
    Page <Author> findAuthorByBirthdateBetween(Pageable pageable, Date start, Date end);

}
