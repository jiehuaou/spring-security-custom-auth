package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)

public class AdminRestTest {
    TestRestTemplate restTemplate;
    URL base;
    @LocalServerPort
    int port;

    @Before
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("admin", "123");
        base = new URL("http://localhost:" + port + "/course");
    }

    @Test
    public void loginAdminThenSuccess() throws IllegalStateException {
        ResponseEntity<String> response =
                restTemplate.getForEntity(base.toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("admin"));
    }

    @Test
    public void loginWrongAdminThenFailed() throws IllegalStateException {
        restTemplate = new TestRestTemplate("admin+", "123*");
        ResponseEntity<String> response =
                restTemplate.getForEntity(base.toString(), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().contains("user is NOT login"));
    }
}

