package com.bezdekova.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookStoreApplicationTests {

    @Value(value="${local.server.port}")
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testRetrieveAllBooks() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/books"),
                HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();        

        ArrayList<String> expected = new ArrayList<>();
        expected.add("{\"name\":\"Babicka\",\"price\":120}");
        expected.add("{\"name\":\"Diva Bara\",\"price\":120}");

        assertEquals(response.getStatusCode().value(), 200, "Response should be 200");
        assertNotNull(responseBody, "Response should not be null");
        assertFalse(response.getBody().length() > 0);

        //JSONAssert.assert(, 200, false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
