package com.bezdekova.book;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(attributePaths = "authorsBooks")
    Book findById(long id);
    
}
