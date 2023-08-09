package com.bezdekova.bookstore.controllers;

import java.net.URI;
import java.util.List;

import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.mappers.AuthorDtoMapper;
import com.bezdekova.bookstore.model.dto.AuthorDto;
import com.bezdekova.bookstore.model.dto.AuthorWithBooksDto;
import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import com.bezdekova.bookstore.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private final AuthorRepository repository;
    private final AuthorService authorService;

    AuthorController(AuthorRepository repository, AuthorService authorService) {
        this.repository = repository;
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorWithBooksDto>> getAllAuthors() {
        List<AuthorWithBooksDto> authorsWithBooks = authorService.getAllAuthorsWithBooks();
        return ResponseEntity.ok(authorsWithBooks);
    }


    // TODO getAuthorsWithoutBooks

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);

        if (author != null) {
            return ResponseEntity.ok(author);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorDto authorDto) {
        Author createdAuthor = authorService.createAuthor(authorDto);
        return ResponseEntity.created(URI.create("/authors/" + createdAuthor.getId())).body(createdAuthor);
    }
   
}
