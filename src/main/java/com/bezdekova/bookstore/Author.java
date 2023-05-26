package com.bezdekova.bookstore;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)     
    private Set<Book> books = new HashSet<>();

    protected Author() {}

    public Author(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Author[id=%d, name=%s, books=%s]", id, name, books);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Book> getBooks() {
        return books;
    }
    
}
