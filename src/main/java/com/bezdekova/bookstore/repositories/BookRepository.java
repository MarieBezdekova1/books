package com.bezdekova.bookstore.repositories;

import com.bezdekova.bookstore.db.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    
}
