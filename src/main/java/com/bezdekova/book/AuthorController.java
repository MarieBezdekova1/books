package com.bezdekova.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorRepository repository;

    AuthorController(AuthorRepository repository) {
        this.repository = repository;
      }

    @GetMapping("/authors")
    Iterable<Author> all() {
        return repository.findAll();
    }
    
}
