package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class JsonDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonDemoApplication.class, args);
    }

@RequestMapping(path = "/api/v1/todos", method = RequestMethod.POST)
public List<Todo> post(@RequestBody List<Todo> todos) {
    return todos;
}

}
