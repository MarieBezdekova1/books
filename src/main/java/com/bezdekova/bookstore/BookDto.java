package com.bezdekova.bookstore;

public class BookDto {

    private String name;
    private Integer price;

    public BookDto() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    };

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

}