package com.bezdekova.bookstore;

import com.bezdekova.bookstore.constant.MappingConstants;
import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.model.request.AuthorRequest;
import com.bezdekova.bookstore.model.request.BookCreateRequest;
import com.bezdekova.bookstore.model.request.BookUpdateRequest;
import com.bezdekova.bookstore.model.response.AuthorResponse;
import com.bezdekova.bookstore.model.response.BookResponse;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        ResponseEntity<String> response = executeCall(MappingConstants.BOOKS, HttpMethod.GET);
        
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");   
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();      
        assertNotNull(responseBody, "Response should not be null.");

        List<String> expected = List.of(book1, book2, book3);
        JSONAssert.assertEquals(expected.toString(), responseBody, false);
    }

    @Test
    public void testRetrieveAllAuthorsWithBooks() throws JSONException {

        ResponseEntity<String> response = executeCall(MappingConstants.AUTHORS, HttpMethod.GET);
        
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

        ResponseEntity<String> response = executeCall(MappingConstants.AUTHORS + "/1", HttpMethod.GET);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();
        assertNotNull(responseBody, "Response should not be null.");

        String authorExpected = "{\"name\":\"Alois Jirasek\",\"books\":[" + book3 + "]}";
        JSONAssert.assertEquals(authorExpected, responseBody, false);
    }

    @Test
    public void testRetrieveOneBook() throws JSONException {

        ResponseEntity<String> response = executeCall(MappingConstants.BOOKS + "/1", HttpMethod.GET);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();
        assertNotNull(responseBody, "Response should not be null.");

        JSONAssert.assertEquals(book3, responseBody, false);
    }

    @Test
    public void testCreateNewAuthor() {

        AuthorRequest authorCreateRequest = new AuthorRequest("Karel Capek");
        AuthorResponse createdAuthor = new AuthorResponse("Karel Capek");
        //createdAuthor.setId(3);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AuthorRequest> request = new HttpEntity<>(authorCreateRequest, headers);

        ResponseEntity<AuthorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/" + MappingConstants.AUTHORS,
                HttpMethod.POST,
                request,
                AuthorResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response should be 201.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        AuthorResponse responseAuthor = response.getBody();
        assertNotNull(responseAuthor, "Response should not be null.");
        assertEquals(createdAuthor, responseAuthor, "Created author is not correct.");
    }

    @Test
    public void testCreateNewBook() {
        BookCreateRequest bookCreateRequest = new BookCreateRequest("Temno", 320, 1);

        Optional<Author> author = authorRepository.findById(1);
        BookResponse createdBook;
        if (author.isPresent()) {
            createdBook = new BookResponse("Temno",320);
        } else {
            throw new RuntimeException("Author by id=1 not found in DB.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<BookCreateRequest> request = new HttpEntity<>(bookCreateRequest, headers);

        ResponseEntity<BookResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/" + MappingConstants.BOOKS,
                HttpMethod.POST,
                request,
                BookResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response should be 201.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        BookResponse responseBook = response.getBody();
        assertNotNull(responseBook, "Response should not be null.");
        assertEquals(createdBook, responseBook, "Created book is not correct.");
    }

    private ResponseEntity<String> executeCall(String uri, HttpMethod method) {
        return restTemplate.exchange(
            "http://localhost:" + port + "/" + uri,
            method, null, String.class);
    }

    @DirtiesContext
    @Test
    public void testUpdateAuthorName() {
        AuthorRequest authorRequest = new AuthorRequest("Alois Jirasek II.");
        AuthorResponse updatedAuthor = new AuthorResponse("Alois Jirasek II.");
        //updatedAuthor.id = 1;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AuthorRequest> request = new HttpEntity<>(authorRequest, headers);

        ResponseEntity<AuthorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/" + MappingConstants.AUTHORS + "/1",
                HttpMethod.PUT,
                request,
                AuthorResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        AuthorResponse responseAuthor = response.getBody();
        assertNotNull(responseAuthor, "Response should not be null.");
        assertEquals(updatedAuthor, responseAuthor, "Updated author is not correct.");
    }

    @DirtiesContext
    @Test
    public void testUpdateBookNameAndPrice() {
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest("Psohlavci 2.", 320);

        Optional<Author> author = authorRepository.findById(1);
        BookResponse createdBook;
        if (author.isPresent()) {
            createdBook = new BookResponse("Psohlavci 2.",320);
        } else {
            throw new RuntimeException("Author by id=1 not found in DB.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<BookUpdateRequest> request = new HttpEntity<>(bookUpdateRequest, headers);

        ResponseEntity<BookResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/" + MappingConstants.BOOKS + "/1",
                HttpMethod.PUT,
                request,
                BookResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        BookResponse responseBook = response.getBody();
        assertNotNull(responseBook, "Response should not be null.");
        assertEquals(createdBook, responseBook, "Updated book is not correct.");
    }

}
