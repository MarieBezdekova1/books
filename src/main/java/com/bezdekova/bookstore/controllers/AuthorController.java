package com.bezdekova.bookstore.controllers;

import com.bezdekova.bookstore.constant.MappingConstants;
import com.bezdekova.bookstore.mappers.response.AuthorResponseMapper;
import com.bezdekova.bookstore.model.request.AuthorRequest;
import com.bezdekova.bookstore.model.response.AuthorResponse;
import com.bezdekova.bookstore.model.response.AuthorWithBooksResponse;
import com.bezdekova.bookstore.services.api.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    // chybí tu mapping MappingConstants - nejspíš jsi to jen nepushnula
    @GetMapping(MappingConstants.AUTHORS)
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorWithBooksResponse> getAllAuthorsWithBooks() {
        return authorService.getAllAuthorsWithBooks()
                .stream()
                .map(authorResponseMapper::map)
                .toList();
    }

    @Operation(
            summary = "Retrieve all authors (without books' details)",
            tags = { "authors", "get" })
    @GetMapping(MappingConstants.AUTHORS_ONLY)
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponse> getAllAuthors() {
        return authorService.getAllAuthors()
                .stream()
                .map(authorResponseMapper::map2)
                .toList();
    }

    @GetMapping(MappingConstants.AUTHORS + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorWithBooksResponse getAuthor(@PathVariable Integer id) {
        return  authorResponseMapper.map(authorService.getAuthorById(id));
    }

    @PostMapping(MappingConstants.AUTHORS)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorWithBooksResponse createAuthor(@RequestBody AuthorRequest authorRequest) {
        return authorResponseMapper.map(authorService.createAuthor(authorRequest));
    }

    @PutMapping(MappingConstants.AUTHORS + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorWithBooksResponse updateAuthor(@PathVariable Integer id, @RequestBody AuthorRequest authorRequest) {
        return authorResponseMapper.map(authorService.updateAuthor(id, authorRequest));
    }
   
}
