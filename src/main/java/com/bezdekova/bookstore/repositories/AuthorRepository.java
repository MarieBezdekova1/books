package com.bezdekova.bookstore.repositories;

import com.bezdekova.bookstore.db.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    
}
