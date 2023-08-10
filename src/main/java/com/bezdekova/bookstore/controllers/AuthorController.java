package com.bezdekova.bookstore.controllers;

import com.bezdekova.bookstore.constant.Mapping;
import com.bezdekova.bookstore.mappers.response.AuthorResponseMapper;
import com.bezdekova.bookstore.model.response.AuthorResponse;
import com.bezdekova.bookstore.services.api.AuthorService;
import java.net.URI;
import java.util.List;

import com.bezdekova.bookstore.model.dto.AuthorDto;
import com.bezdekova.bookstore.db.Author;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
// ideálně toto smazat a řešit to per EP
@RequestMapping(Mapping.AUTHORS)
@Tag(name = "Authors", description = "Author APIs")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorResponseMapper authorResponseMapper;

    AuthorController(AuthorService authorService, AuthorResponseMapper authorResponseMapper) {
        this.authorService = authorService;
        this.authorResponseMapper = authorResponseMapper;
    }

    @Operation(
            summary = "Retrieve all authors with their books",
            tags = { "authors", "get" })
    @GetMapping
    // používat response status a response objekty
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponse> getAllAuthorsWithBooks() {
        return authorService.getAllAuthorsWithBooks()
            .stream()
            .map(authorResponseMapper::map)
            .toList();
    }

    @Operation(
            summary = "Retrieve all authors (without books' details)",
            tags = { "authors", "get" })
    @GetMapping("/only")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        var authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);

        // přesun do service pomocí exceptions -- throw new NotFoundException (tvoje vlastní exception)
        if (author != null) {
            return ResponseEntity.ok(author);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            //return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorDto authorDto) {
        Author createdAuthor = authorService.createAuthor(authorDto);
        return ResponseEntity.created(URI.create("/authors/" + createdAuthor.getId())).body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        Author updatedAuthor = authorService.updateAuthor(id, authorDto);

        if (updatedAuthor != null) {
            return ResponseEntity.ok(updatedAuthor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
   
}
