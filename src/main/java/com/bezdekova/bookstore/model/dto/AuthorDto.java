package com.bezdekova.bookstore.model.dto;

public class AuthorDto {

    private String name;

    public AuthorDto() {
    }

    public AuthorDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}