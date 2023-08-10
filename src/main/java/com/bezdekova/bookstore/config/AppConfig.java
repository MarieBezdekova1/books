package com.bezdekova.bookstore.config;

import com.bezdekova.bookstore.mappers.BookDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bezdekova.bookstore.mappers.AuthorDtoMapper;

@Configuration
public class AppConfig {

    @Bean
    public AuthorDtoMapper authorDtoMapper(BookDtoMapper bookDtoMapper) {
        return new AuthorDtoMapper(bookDtoMapper);
    }

    @Bean
    public BookDtoMapper bookDtoMapper() {
        return new BookDtoMapper();
    }
}