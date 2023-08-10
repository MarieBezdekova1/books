package com.bezdekova.bookstore.model.response;

import com.bezdekova.bookstore.model.dto.BookDto;
import java.util.List;

public record AuthorResponse(
    String name,
    List<BookDto> books
) {

}
