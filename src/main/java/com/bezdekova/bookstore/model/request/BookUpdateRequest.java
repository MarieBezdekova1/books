package com.bezdekova.bookstore.model.request;

public record BookUpdateRequest(
        String name,
        Integer price
) {
}