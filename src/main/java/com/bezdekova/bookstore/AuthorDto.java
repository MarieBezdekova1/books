package com.bezdekova.bookstore;

import java.util.Set;

public class AuthorDto {

    private String name;
    private Set<Book> books;

    public AuthorDto() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    };

    public String getName() {
        return name;
    }

    public Set<Book> getBooks() {
        return books;
    }

}