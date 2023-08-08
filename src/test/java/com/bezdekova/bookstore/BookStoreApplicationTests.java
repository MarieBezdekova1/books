package com.bezdekova.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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

        ResponseEntity<String> response = executeCall("/books");  
        
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");   
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();      
        assertNotNull(responseBody, "Response should not be null.");

        List<String> expected = List.of(book1, book2, book3);
        JSONAssert.assertEquals(expected.toString(), responseBody, false);
    }

    @Test
    public void testRetrieveAllAuthors() throws JSONException {

        ResponseEntity<String> response = executeCall("/authors");  
        
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response should be 200.");   
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(), "Type should be application/json.");

        String responseBody = response.getBody();      
        assertNotNull(responseBody, "Response should not be null.");

        String author1 = "{\"name\":\"Bozena Nemcova\",\"books\":[" + book1 + ", " +  book2 + "]}";
        String author2 = "{\"name\":\"Alois Jirasek\",\"books\":[" + book3 + "]}";
        List<String> expected = List.of(author1, author2);
        JSONAssert.assertEquals(expected.toString(), responseBody, false);
    }

    private ResponseEntity<String> executeCall(String uri) {
        return restTemplate.exchange(
            "http://localhost:" + port + uri,
            HttpMethod.GET, null, String.class);  
    }

}
