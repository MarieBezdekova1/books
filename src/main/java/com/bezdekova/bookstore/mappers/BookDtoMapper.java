package com.bezdekova.bookstore.mappers;

import com.bezdekova.bookstore.db.Book;
import com.bezdekova.bookstore.model.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

public class BookDtoMapper {
    public BookDto bookToBookDto(Book book) {
        return new BookDto(book.getName(), book.getPrice());
    }

    public List<BookDto> booksToBookDtos(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookDto> list = new ArrayList<>();
        for ( Book book : books ) {
            list.add( bookToBookDto( book ) );
        }
        return list;
    }
}