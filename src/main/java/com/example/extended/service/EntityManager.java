package com.example.extended.service;

import com.example.extended.dto.AuthorDTO;
import com.example.extended.dto.BookDTO;
import com.example.extended.model.Author;
import com.example.extended.model.Book;
import com.example.extended.pojo.SimplePage;
import com.example.extended.repository.AuthorRepository;
import com.example.extended.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EntityManager {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public AuthorDTO authorToDto(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setBirthdate(author.getBirthdate());
        return authorDTO;
    }

    public BookDTO bookToDto(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIssueYear(book.getIssueYear());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setAuthorid(book.getAuthor().getId());
        return bookDTO;
    }

    public Page<AuthorDTO> listToPageA(List<AuthorDTO> authorDTOList, Pageable pageable, long total){
        /*int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), authorDTOList.size());*/
        Page<AuthorDTO> page = new PageImpl<>(authorDTOList, pageable, total);

        return page;
    }
    public Page<BookDTO> listToPageB(List<BookDTO> authorDTOList, Pageable pageable){
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), authorDTOList.size());
        Page<BookDTO> page = new PageImpl<>(authorDTOList.subList(start, end), pageable, authorDTOList.size());

        return page;
    }
    @Transactional
    public List<AuthorDTO> getAllA(){
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        authors.forEach(author -> authorDTOList.add(authorToDto(author)));
        return authorDTOList;
    }
    @Transactional
    public List<BookDTO> getAllB(){
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(book -> bookDTOList.add(bookToDto(book)));
        return bookDTOList;
    }
    @Transactional
    public SimplePage<AuthorDTO> getAllAuthors(Pageable pageable){
        Page<Author> authors = authorRepository.findAll(pageable);
        /*List<AuthorDTO> authorDTOList = new ArrayList<>();
        authors.forEach(author -> authorDTOList.add(authorToDto(author)));

        return listToPageA(authorDTOList, pageable, authors.getTotalElements());*/

        return new SimplePage<>(authors.map(author -> authorToDto(author)));
    }

    @Transactional
    public SimplePage<BookDTO> getAllBooks(Pageable pageable){
        Page<Book> books = bookRepository.findAll(pageable);
//        List<BookDTO> bookDTOList = new ArrayList<>();
//        books.forEach(book -> bookDTOList.add(bookToDto(book)));

        return new SimplePage<>(books.map(book -> bookToDto(book)));
    }

    @Transactional
    public Page<AuthorDTO> getAuthorByDateBetween(Pageable pageable, Date start, Date end){
        Page<Author> authors = authorRepository.findAuthorByBirthdateBetween(pageable, start, end);
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        authors.forEach(author -> authorDTOList.add(authorToDto(author)));

        return listToPageA(authorDTOList, pageable, authors.getTotalElements());
    }

    @Transactional
    public Page<BookDTO> getAllBooksByName(Pageable pageable, String title){
        Page<Book> books = bookRepository.findAllByTitle(pageable,title);
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(book -> bookDTOList.add(bookToDto(book)));

        return listToPageB(bookDTOList, pageable);
    }
    @Transactional
    public Page<BookDTO> getAllBooksByNameAndDate(Pageable pageable, String title, Long year){
        Page<Book> books = bookRepository.findAllByTitleAndIssueYear(pageable,title, year);
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(book -> bookDTOList.add(bookToDto(book)));

        return listToPageB(bookDTOList, pageable);
    }
    @Transactional
    public Page<Book> getAuthorAndCount(Pageable pageable){
        return bookRepository.countBookByAuthor_Id(pageable);
    }
    @Transactional
    public Page<Book> countBookByAuthorAndYear(Pageable pageable, Date start, Date end){
        return bookRepository.countBookByAuthorAndYear(pageable, start ,end);
    }

    public int sum(int a, int b){
        return a+b;
    }
    public Date date(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 2);
        return calendar.getTime();
    }
    public String createBook(Book book){
        bookRepository.saveAndFlush(book);
        return "Book created";
    }
    public String updateBook(Book book, Long id){
        bookRepository.findById(id).map(book1 -> {
            book1.setTitle(book.getTitle());
            book1.setDescription(book.getDescription());
            book1.setIssueYear(book.getIssueYear());
            book1.setAuthor(book.getAuthor());
            return bookRepository.save(book1);
        }).orElseGet(()->{
            return bookRepository.save(book);
        });
        return "Successfully updated";
    }
    public String deleteBook(Long id){
        bookRepository.deleteById(id);
        return "Book successfully deleted";
    }
    public String createAuthor(Author author){
        authorRepository.saveAndFlush(author);
        return "Author created!";
    }
    public String editAuthor(Author author, Long id){
        authorRepository.findById(id).map(author1 -> {
            author1.setName(author.getName());
            author1.setBirthdate(author.getBirthdate());
            author1.setBookList(author1.getBookList());
            return authorRepository.save(author1);
        }).orElseGet(()->{
            return authorRepository.save(author);
        });
        return "Successfully updated";
    }
    public String deleteAuthor(Long id){
        authorRepository.deleteById(id);
        return "Author successfully deleted";
    }

}
