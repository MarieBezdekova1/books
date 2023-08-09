package com.bezdekova.bookstore.services;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.mappers.BookDtoMapper;
import com.bezdekova.bookstore.model.dto.BookCreateDto;
import com.bezdekova.bookstore.model.dto.BookDto;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import com.bezdekova.bookstore.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookDtoMapper bookDtoMapper;


    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookDtoMapper bookDtoMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookDtoMapper = bookDtoMapper;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookDtoMapper.booksToBookDtos(books);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book createBook(BookCreateDto bookDto) {
        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        Book book = new Book(bookDto.getName(), author, bookDto.getPrice());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setName(bookDto.getName());
            book.setPrice(bookDto.getPrice());
            return bookRepository.save(book);
        } else {
            return null;
        }
    }
}
