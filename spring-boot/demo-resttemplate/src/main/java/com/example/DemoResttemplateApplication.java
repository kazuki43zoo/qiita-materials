package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

@SpringBootApplication
public class DemoResttemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoResttemplateApplication.class, args);
    }

    @RestController
    static class DemoRestController {

        private final RestOperations restOperations;

        public DemoRestController(RestTemplateBuilder restTemplateBuilder) {
            this.restOperations = restTemplateBuilder
                    .build();
        }

        @GetMapping("/readme")
        String readme() {
            return restOperations.getForObject("https://raw.githubusercontent.com/kazuki43zoo/qiita-materials/master/README.md", String.class);
        }
    }

}
