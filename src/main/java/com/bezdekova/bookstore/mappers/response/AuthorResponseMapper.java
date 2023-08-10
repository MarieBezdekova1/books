package com.bezdekova.bookstore.mappers.response;

import com.bezdekova.bookstore.model.dto.AuthorWithBooksDto;
import com.bezdekova.bookstore.model.response.AuthorResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthorResponseMapper {

  public AuthorResponse map(AuthorWithBooksDto author) {
    return new AuthorResponse(author.getName(), author.getBooks());
  }

}
