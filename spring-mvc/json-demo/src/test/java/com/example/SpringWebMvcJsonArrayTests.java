package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JsonDemoApplication.class)
@WebIntegrationTest(randomPort = true)
public class SpringWebMvcJsonArrayTests {

    @Value("http://localhost:${local.server.port:8080}/api/v1/todos")
    private URI uri;

    private final RestTemplate restTemplate = new RestTemplate();

    private final List<Todo> requestTodoList = Arrays.asList(
            new Todo("Jsckson勉強会")
            , new Todo("飲み会")
    );

    @Test
    public void array() throws IOException {

        Todo[] todoArray = restTemplate.postForObject(uri, requestTodoList, Todo[].class);
        for (Todo todo : todoArray) {
            System.out.println(todo.getTitle());
        }

    }

    @Test
    public void arrayConvertListUsingArraysAsList() {

        List<Todo> todoList = Arrays.asList(restTemplate.postForObject(uri, requestTodoList, Todo[].class));
        for (Todo todo : todoList) {
            System.out.println(todo.getTitle());
        }

    }

    @Test
    public void listUsingParameterizedTypeReference() {
        RequestEntity<List<Todo>> requestEntity = RequestEntity.post(uri).body(requestTodoList);
        ResponseEntity<List<Todo>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<Todo>>() {});
        for (Todo todo : responseEntity.getBody()) {
            System.out.println(todo.getTitle());
        }
    }

}
