package com.bezdekova.bookstore.services;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.model.request.BookCreateRequest;
import com.bezdekova.bookstore.model.request.BookUpdateRequest;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import com.bezdekova.bookstore.repositories.BookRepository;
import com.bezdekova.bookstore.services.api.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found."));
    }

    public Book createBook(BookCreateRequest bookCreateRequest) {
        // lepší používat var
        Author author = authorRepository.findById(bookCreateRequest.authorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        // toto by se ideálně mělo přesunout do nově vzniklého AuthorDomainMapperu (podobně jako AuthorResponseMapper bude vytvářet jen objekt)
        // lepší používat var
        Book book = new Book(bookCreateRequest.name(), author, bookCreateRequest.price());
        return bookRepository.save(book);
    }

    public Book updateBook(Integer id, BookUpdateRequest bookUpdateRequest) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            // toto by se ideálně mělo přesunout do nově vzniklého AuthorDomainMapperu (podobně jako AuthorResponseMapper bude vytvářet jen objekt)
            // lepší používat var
            Book book = optionalBook.get();
            book.setName(bookUpdateRequest.name());
            book.setPrice(bookUpdateRequest.price());
            return bookRepository.save(book);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found.");
        }
    }
}
