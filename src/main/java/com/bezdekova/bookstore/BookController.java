package com.bezdekova.bookstore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
      }

    @GetMapping("/books")
    List<BookDto> getAllBooks() {
        Iterable<Book> books = repository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book : books) {
            BookDto bookDto = new BookDto();
            bookDto.setName(book.getName());
            bookDto.setPrice(book.getPrice());
            bookDtos.add(bookDto);
        }

        return bookDtos;
    }
    
}
