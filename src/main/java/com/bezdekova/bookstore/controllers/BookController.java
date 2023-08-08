package com.bezdekova.bookstore.controllers;

import java.util.List;

import com.bezdekova.bookstore.mappers.BookDtoMapper;
import com.bezdekova.bookstore.model.dto.BookDto;
import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.repositories.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookDtoMapper bookDtoMapper;
    private final BookRepository repository;

    BookController(BookRepository repository, BookDtoMapper bookDtoMapper) {
        this.repository = repository;
        this.bookDtoMapper = bookDtoMapper;
      }

    @GetMapping("/books")
    List<BookDto> getAllBooks() {
        List<Book> books = repository.findAll();
        return bookDtoMapper.booksToBookDtos(books);
    }
    
}
