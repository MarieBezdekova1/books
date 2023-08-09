package com.bezdekova.bookstore.controllers;

import java.net.URI;
import java.util.List;

import com.bezdekova.bookstore.model.dto.AuthorDto;
import com.bezdekova.bookstore.model.dto.AuthorWithBooksDto;
import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import com.bezdekova.bookstore.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authors")
@Tag(name = "Authors", description = "Author APIs")
public class AuthorController {

    private final AuthorRepository repository;
    private final AuthorService authorService;

    AuthorController(AuthorRepository repository, AuthorService authorService) {
        this.repository = repository;
        this.authorService = authorService;
    }

    @Operation(
            summary = "Retrieve all authors with their books",
            tags = { "authors", "get" })
    @GetMapping
    public ResponseEntity<List<AuthorWithBooksDto>> getAllAuthorsWithBooks() {
        List<AuthorWithBooksDto> authorsWithBooks = authorService.getAllAuthorsWithBooks();
        return ResponseEntity.ok(authorsWithBooks);
    }

    @Operation(
            summary = "Retrieve all authors (without books' details)",
            tags = { "authors", "get" })
    @GetMapping("/only")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

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
