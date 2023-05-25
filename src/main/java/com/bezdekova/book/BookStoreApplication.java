package com.bezdekova.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AuthorRepository authorRep, BookRepository bookRep) {
		return (args) -> {
			Author author1 = new Author("Bozena Nemcova");
			Author author2 = new Author("Alois Jirasek");
			authorRep.save(author1);
			authorRep.save(author2);
			
			bookRep.save(new Book("Babicka", author1, 120));
			bookRep.save(new Book("Diva Bara", author1, 420));
			bookRep.save(new Book("Psohlavci", author2, 300));
			
		};
	}

}
