package com.bezdekova.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.model.dto.AuthorDto;
import com.bezdekova.bookstore.model.dto.AuthorNoBooksDto;
import com.bezdekova.bookstore.model.dto.BookCreateDto;
import com.bezdekova.bookstore.model.dto.BookDto;
import com.bezdekova.bookstore.repositories.AuthorRepository;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = BookStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookStoreApplicationTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    AuthorRepository authorRepository;

    private final String book1 = "{\"name\":\"Babicka\",\"price\":150}";
    private final String book2 = "{\"name\":\"Diva Bara\",\"price\":450}";
    private final String book3 = "{\"name\":\"Psohlavci\",\"price\":300}";
    
    @Test
    public void testRetrieveAllBooks() throws JSONException {

        ResponseEntity<String> response = executeCall("/books", HttpMethod.GET);
        
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");   
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();      
        assertNotNull(responseBody, "Response should not be null.");

        List<String> expected = List.of(book1, book2, book3);
        JSONAssert.assertEquals(expected.toString(), responseBody, false);
    }

    @Test
    public void testRetrieveAllAuthorsWithBooks() throws JSONException {

        ResponseEntity<String> response = executeCall("/authors", HttpMethod.GET);
        
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");   
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();      
        assertNotNull(responseBody, "Response should not be null.");

        String author1 = "{\"name\":\"Bozena Nemcova\",\"books\":[" + book1 + ", " +  book2 + "]}";
        String author2 = "{\"name\":\"Alois Jirasek\",\"books\":[" + book3 + "]}";
        List<String> expected = List.of(author1, author2);
        JSONAssert.assertEquals(expected.toString(), responseBody, false);
    }

    @Test
    public void testRetrieveOneAuthorWithBook() throws JSONException {

        ResponseEntity<String> response = executeCall("/authors/1", HttpMethod.GET);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();
        assertNotNull(responseBody, "Response should not be null.");

        String authorExpected = "{\"name\":\"Alois Jirasek\",\"books\":[" + book3 + "]}";
        JSONAssert.assertEquals(authorExpected, responseBody, false);
    }

    @Test
    public void testRetrieveOneBook() throws JSONException {

        ResponseEntity<String> response = executeCall("/books/1", HttpMethod.GET);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();
        assertNotNull(responseBody, "Response should not be null.");

        JSONAssert.assertEquals(book3, responseBody, false);
    }

    @Test
    public void testCreateNewAuthor() {

        AuthorDto authorCreateDTO = new AuthorDto("Karel Capek");
        Author createdAuthor = new Author("Karel Capek");
        createdAuthor.setId(3);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AuthorDto> request = new HttpEntity<>(authorCreateDTO, headers);

        ResponseEntity<Author> response = restTemplate.exchange(
                "http://localhost:" + port + "/authors",
                HttpMethod.POST,
                request,
                Author.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response should be 201.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        Author responseAuthor = response.getBody();
        assertNotNull(responseAuthor, "Response should not be null.");
        assertEquals(createdAuthor, responseAuthor, "Created author is not correct.");
    }

    @Test
    public void testCreateNewBook() {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("Temno");
        bookCreateDto.setPrice(320);
        bookCreateDto.setAuthorId(1L);

        Optional<Author> author = authorRepository.findById(1L);
        BookDto createdBook;
        if (author.isPresent()) {
            createdBook = new BookDto("Temno",320);
        } else {
            throw new RuntimeException("Author by id=1 not found in DB.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<BookCreateDto> request = new HttpEntity<>(bookCreateDto, headers);

        ResponseEntity<BookDto> response = restTemplate.exchange(
                "http://localhost:" + port + "/books",
                HttpMethod.POST,
                request,
                BookDto.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response should be 201.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        BookDto responseBook = response.getBody();
        assertNotNull(responseBook, "Response should not be null.");
        assertEquals(createdBook, responseBook, "Created book is not correct.");
    }

    private ResponseEntity<String> executeCall(String uri, HttpMethod method) {
        return restTemplate.exchange(
            "http://localhost:" + port + uri,
            method, null, String.class);
    }

    @DirtiesContext
    @Test
    public void testUpdateAuthorName() {
        AuthorDto authorUpdateDto = new AuthorDto("Alois Jirasek II.");
        AuthorNoBooksDto updatedAuthor = new AuthorNoBooksDto(1L, "Alois Jirasek II.");
        updatedAuthor.setId(1L);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AuthorDto> request = new HttpEntity<>(authorUpdateDto, headers);

        ResponseEntity<AuthorNoBooksDto> response = restTemplate.exchange(
                "http://localhost:" + port + "/authors/1",
                HttpMethod.PUT,
                request,
                AuthorNoBooksDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        AuthorNoBooksDto responseAuthor = response.getBody();
        assertNotNull(responseAuthor, "Response should not be null.");
        assertEquals(updatedAuthor, responseAuthor, "Updated author is not correct.");
    }

    @DirtiesContext
    @Test
    public void testUpdateBookNameAndPrice() {
        BookDto bookUpdateDto = new BookDto("Psohlavci 2.", 320);

        Optional<Author> author = authorRepository.findById(1L);
        BookDto createdBook;
        if (author.isPresent()) {
            createdBook = new BookDto("Psohlavci 2.",320);
        } else {
            throw new RuntimeException("Author by id=1 not found in DB.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<BookDto> request = new HttpEntity<>(bookUpdateDto, headers);

        ResponseEntity<BookDto> response = restTemplate.exchange(
                "http://localhost:" + port + "/books/1",
                HttpMethod.PUT,
                request,
                BookDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        BookDto responseBook = response.getBody();
        assertNotNull(responseBook, "Response should not be null.");
        assertEquals(createdBook, responseBook, "Updated book is not correct.");
    }

}
