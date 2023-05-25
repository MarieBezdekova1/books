package com.bezdekova.bookstore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
      }

    @GetMapping("/books")
    Iterable<Book> all() {
        return repository.findAll();
    }
    
}
