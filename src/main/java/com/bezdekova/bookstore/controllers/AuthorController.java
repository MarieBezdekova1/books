package com.bezdekova.bookstore.controllers;

import java.util.List;

import com.bezdekova.bookstore.mappers.AuthorDtoMapper;
import com.bezdekova.bookstore.model.dto.AuthorWithBooksDto;
import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorDtoMapper authorDtoMapper;
    private final AuthorRepository repository;

    AuthorController(AuthorRepository repository, AuthorDtoMapper authorDtoMapper) {
        this.repository = repository;
        this.authorDtoMapper = authorDtoMapper;
      }

    @GetMapping("/authors")
    public List<AuthorWithBooksDto> getAllAuthorsWithBooks() {
        List<Author> authors = repository.findAll();
        List<AuthorWithBooksDto> authorWithBooksDtos = authorDtoMapper.authorsToAuthorsDtos(authors);

        return authorWithBooksDtos;
    }

    // TODO getAuthorsWithoutBooks
   
}
