package com.bezdekova.bookstore.model.dto;

import java.util.List;

public record AuthorWithBooksDto(
        String name,
        List<BookDto> books
) {
}