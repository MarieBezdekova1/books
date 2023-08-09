package com.bezdekova.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import com.bezdekova.bookstore.db.Author;
import com.bezdekova.bookstore.model.dto.AuthorDto;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(classes = BookStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookStoreApplicationTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

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
        JSONAssert.assertEquals(authorExpected.toString(), responseBody, false);
    }

    @Test
    public void testRetrieveOneBook() throws JSONException {

        ResponseEntity<String> response = executeCall("/books/1", HttpMethod.GET);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();
        assertNotNull(responseBody, "Response should not be null.");

        JSONAssert.assertEquals(book3.toString(), responseBody, false);
    }

    @Test
    public void testCreateNewAuthos() throws JSONException {

        AuthorDto authorCreateDTO = new AuthorDto();
        authorCreateDTO.setName("Karel Capek");
        Author createdAuthor = new Author("Karel Capek");
        //createdAuthor.setId(3L);
        String authorJson = "{\"id\":3,\"name\":\"Karel Capek\",\"books\":[]}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<AuthorDto> request = new HttpEntity<>(authorCreateDTO, headers);

        //ResponseEntity<String> response = executeCall("/authors", HttpMethod.POST);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/authors",
                HttpMethod.POST,
                request,
               // Author.class
                String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response should be 201.");
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();
        assertNotNull(responseBody, "Response should not be null.");

        JSONAssert.assertEquals(authorJson.toString(), responseBody, false);
    }

    private ResponseEntity<String> executeCall(String uri, HttpMethod method) {
        return restTemplate.exchange(
            "http://localhost:" + port + uri,
            method, null, String.class);
    }

}
