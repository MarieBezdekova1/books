package com.bezdekova.bookstore.model.request;

public record BookCreateRequest(
        String name,
        Integer price,
        Integer authorId
) {
}
