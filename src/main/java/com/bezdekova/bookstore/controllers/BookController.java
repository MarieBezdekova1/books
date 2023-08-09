package com.bezdekova.bookstore.controllers;

import java.net.URI;
import java.util.List;

import com.bezdekova.bookstore.model.dto.BookCreateDto;
import com.bezdekova.bookstore.model.dto.BookDto;
import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import com.bezdekova.bookstore.repositories.BookRepository;
import com.bezdekova.bookstore.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final BookService bookService;

    BookController(BookRepository bookRepository, AuthorRepository authorRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

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
    
}
