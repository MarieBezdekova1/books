package com.bezdekova.bookstore.services;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.model.request.AuthorRequest;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import com.bezdekova.bookstore.services.api.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthorsWithBooks() {
        return authorRepository.findAll();
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found."));
    }

    public Author createAuthor(AuthorRequest authorRequest) {
        Author author = new Author(authorRequest.name());
        return authorRepository.save(author);
    }

    public Author updateAuthor(Integer id, AuthorRequest authorRequest) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setName(authorRequest.name());
            return authorRepository.save(author);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found.");
        }
    }
}