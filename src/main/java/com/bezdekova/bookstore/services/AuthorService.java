package com.bezdekova.bookstore.services;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.mappers.AuthorDtoMapper;
import com.bezdekova.bookstore.model.dto.AuthorDto;
import com.bezdekova.bookstore.model.dto.AuthorWithBooksDto;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorDtoMapper authorDtoMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorDtoMapper authorDtoMapper) {
        this.authorRepository = authorRepository;
        this.authorDtoMapper = authorDtoMapper;
    }

    public List<AuthorWithBooksDto> getAllAuthorsWithBooks() {
        List<Author> authors = authorRepository.findAll();
        return authorDtoMapper.authorsToAuthorsWithBooksDtos(authors);
    }

    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authorDtoMapper.authorsToAuthorsDtos(authors);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author createAuthor(AuthorDto authorDto) {
        Author author = new Author(authorDto.getName());
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, AuthorDto authorDto) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setName(authorDto.getName());
            return authorRepository.save(author);
        } else {
            return null;
        }
    }
}