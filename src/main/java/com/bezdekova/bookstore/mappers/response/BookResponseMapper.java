package com.bezdekova.bookstore.mappers.response;

import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.model.response.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookResponseMapper {

    public BookResponse map(Book book) {
        return new BookResponse(book.getName(), book.getPrice());
    }

}
