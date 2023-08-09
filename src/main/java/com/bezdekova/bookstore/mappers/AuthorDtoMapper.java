package com.bezdekova.bookstore.mappers;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.model.dto.AuthorDto;
import com.bezdekova.bookstore.model.dto.AuthorWithBooksDto;

import java.util.ArrayList;
import java.util.List;

public class AuthorDtoMapper {

    private final BookDtoMapper bookDtoMapper;

    public AuthorDtoMapper(BookDtoMapper bookDtoMapper) {
        this.bookDtoMapper = bookDtoMapper;
    }

    public AuthorWithBooksDto authorToAuthorWithBooksDto(Author author) {
        return new AuthorWithBooksDto(author.getName(), bookDtoMapper.booksToBookDtos(author.getBooks()));
    }

    public AuthorDto authorToAuthorDto(Author author) {
        return new AuthorDto(author.getName());
    }

    public List<AuthorWithBooksDto> authorsToAuthorsWithBooksDtos(List<Author> authors) {
        if ( authors == null ) {
            return null;
        }

        List<AuthorWithBooksDto> list = new ArrayList<>();
        for (Author author : authors) {
            list.add(authorToAuthorWithBooksDto(author));
        }
        return list;
    }

    public List<AuthorDto> authorsToAuthorsDtos(List<Author> authors) {
        if ( authors == null ) {
            return null;
        }

        List<AuthorDto> list = new ArrayList<>();
        for (Author author : authors) {
            list.add(authorToAuthorDto(author));
        }
        return list;
    }
}