package com.example.extended.repository;

import com.example.extended.model.Author;
import com.example.extended.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);
    Page<Book> findAllByTitle(Pageable pageable, String title);
    Page<Book> findAllByTitleAndIssueYear(Pageable pageable, String title, Long year);

    @Query("SELECT COUNT(*), a.id, a.name, a.birthdate FROM Book b INNER JOIN Author a ON a.id = b.author.id GROUP BY a.id, a.name")
    Page<Book> countBookByAuthor_Id(Pageable pageable);

    @Query("SELECT COUNT(*), a.id, a.name, a.birthdate FROM Book b " +
            "INNER JOIN Author a ON a.id = b.author.id WHERE a.birthdate>?1 AND a.birthdate<?2 GROUP BY a.id, a.name")
    Page<Book> countBookByAuthorAndYear(Pageable pageable, Date start, Date end);
}
