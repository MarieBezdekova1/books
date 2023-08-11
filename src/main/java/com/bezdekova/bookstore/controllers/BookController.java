package com.bezdekova.bookstore.controllers;

import com.bezdekova.bookstore.constant.MappingConstants;
import com.bezdekova.bookstore.mappers.response.BookResponseMapper;
import com.bezdekova.bookstore.model.request.BookCreateRequest;
import com.bezdekova.bookstore.model.request.BookUpdateRequest;
import com.bezdekova.bookstore.model.response.BookResponse;
import com.bezdekova.bookstore.services.api.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Books", description = "Book APIs")
public class BookController {

    private final BookService bookService;
    private final BookResponseMapper bookResponseMapper;

    BookController(BookService bookService, BookResponseMapper bookResponseMapper) {
        this.bookService = bookService;
        this.bookResponseMapper = bookResponseMapper;
    }

    @Operation(
            summary = "Retrieve all books",
            tags = { "books", "get" })
    @GetMapping(MappingConstants.BOOKS)
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(bookResponseMapper::map)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(MappingConstants.BOOKS + "/{id}")
    public BookResponse getBook(@PathVariable Integer id) {
        return bookResponseMapper.map(bookService.getBookById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(MappingConstants.BOOKS)
    public BookResponse createBook(@RequestBody BookCreateRequest bookCreateRequest) {
        return bookResponseMapper.map(bookService.createBook(bookCreateRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(MappingConstants.BOOKS + "/{id}")
    public BookResponse updateBook(@PathVariable Integer id, @RequestBody BookUpdateRequest bookUpdateRequest) {
        return bookResponseMapper.map(bookService.updateBook(id, bookUpdateRequest));
    }
    
}
