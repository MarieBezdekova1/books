package com.bezdekova.bookstore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorRepository repository;

    AuthorController(AuthorRepository repository) {
        this.repository = repository;
      }

    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors() {
        Iterable<Author> authors = repository.findAll();
        List<AuthorDto> authorDtos = new ArrayList<>();

        for (Author author : authors) {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setName(author.getName());
            authorDto.setBooks(author.getBooks());
            authorDtos.add(authorDto);
        }

        return authorDtos;
    }
   
}
