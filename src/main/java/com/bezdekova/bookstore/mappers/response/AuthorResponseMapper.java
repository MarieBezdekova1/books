package com.bezdekova.bookstore.mappers.response;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.model.response.AuthorResponse;
import com.bezdekova.bookstore.model.response.AuthorWithBooksResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthorResponseMapper {

    private final BookResponseMapper bookResponseMapper;

    public AuthorResponseMapper(BookResponseMapper bookResponseMapper) {
        this.bookResponseMapper = bookResponseMapper;
    }

    public AuthorWithBooksResponse map(Author author) {
        return new AuthorWithBooksResponse(
                author.getName(),
                author.getBooks().stream().map(bookResponseMapper::map).toList());
    }

    public AuthorResponse map2(Author author) {
        return new AuthorResponse(author.getName());
    }

}
