package com.bezdekova.bookstore.model.response;

import java.util.List;

public record AuthorWithBooksResponse(
        String name,
        List<BookResponse> books
) {
}
