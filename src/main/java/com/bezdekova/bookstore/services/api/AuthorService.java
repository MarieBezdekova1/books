package com.bezdekova.bookstore.services.api;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.model.dto.AuthorDto;
import com.bezdekova.bookstore.model.dto.AuthorWithBooksDto;
import java.util.List;

public interface AuthorService {
  List<AuthorWithBooksDto> getAllAuthorsWithBooks();
  List<AuthorDto> getAllAuthors();
  Author getAuthorById(Long id);
  Author createAuthor(AuthorDto authorDto);
  Author updateAuthor(Long id, AuthorDto authorDto);
}
