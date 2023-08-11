package com.bezdekova.bookstore.services.api;

import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.model.request.BookCreateRequest;
import com.bezdekova.bookstore.model.request.BookUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Integer id);
    Book createBook(BookCreateRequest bookCreateRequest);
    Book updateBook(Integer id, BookUpdateRequest bookUpdateRequest);
}
