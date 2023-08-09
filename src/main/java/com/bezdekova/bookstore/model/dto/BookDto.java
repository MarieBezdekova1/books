package com.bezdekova.bookstore.model.dto;

import java.util.Objects;

public class BookDto {

    private String name;
    private Integer price;

    public BookDto() {}

    public BookDto(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(name, bookDto.name) && Objects.equals(price, bookDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}