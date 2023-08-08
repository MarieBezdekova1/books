package com.bezdekova.bookstore.model.dto;

import java.util.List;

public class AuthorWithBooksDto {

    private String name;
    private List<BookDto> books;

    public AuthorWithBooksDto() {}

    public AuthorWithBooksDto(String name, List<BookDto> books) {
        this.name = name;
        this.books = books;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    };

    public String getName() {
        return name;
    }

    public List<BookDto> getBooks() {
        return books;
    }

}