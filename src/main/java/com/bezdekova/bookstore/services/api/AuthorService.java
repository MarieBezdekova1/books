package com.bezdekova.bookstore.services.api;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.model.request.AuthorRequest;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthorsWithBooks();
    List<Author> getAllAuthors();
    Author getAuthorById(Integer id);
    Author createAuthor(AuthorRequest authorRequest);
    Author updateAuthor(Integer id, AuthorRequest authorRequest);
}
