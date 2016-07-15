package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ObjectMapperJsonArrayTests {

    private static final String ARRAY_JSON = new StringBuilder()
            .append("[")
            .append("  {")
            .append("    \"title\" : \"Jsckson勉強会\"")
            .append("  }")
            .append("  ,")
            .append("  {")
            .append("    \"title\" : \"飲み会\"")
            .append("  }")
            .append("]").toString();

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    public void array() throws IOException {
        Todo[] todoArray = jsonMapper.readValue(ARRAY_JSON, Todo[].class);
        for (Todo todo : todoArray) {
            System.out.println(todo.getTitle());
        }
    }

    @Test
    public void arrayConvertListUsingArraysAsList() throws IOException {
        List<Todo> todoList = Arrays.asList(jsonMapper.readValue(ARRAY_JSON, Todo[].class));
        for (Todo todo : todoList) {
            System.out.println(todo.getTitle());
        }
    }

    @Test
    public void listUsingTypeReference() throws IOException {
        List<Todo> todoList = jsonMapper.readValue(ARRAY_JSON, new TypeReference<List<Todo>>() {});
        for (Todo todo : todoList) {
            System.out.println(todo.getTitle());
        }
    }

    static class Todo {
        private String title;
        private boolean finished;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }
    }

}
