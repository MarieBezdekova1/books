package com.bezdekova.bookstore.controllers;

import java.net.URI;
import java.util.List;

import com.bezdekova.bookstore.model.dto.BookCreateDto;
import com.bezdekova.bookstore.model.dto.BookDto;
import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@Tag(name = "Books", description = "Book APIs")
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Retrieve all books",
            tags = { "books", "get" })
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookCreateDto bookCreateDto) {
        Book createdBook = bookService.createBook(bookCreateDto);
        return ResponseEntity.created(URI.create("/api/books/" + createdBook.getId())).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        Book updatedBook = bookService.updateBook(id, bookDto);

        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
