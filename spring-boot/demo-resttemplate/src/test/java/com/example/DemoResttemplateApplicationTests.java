package com.example;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoResttemplateApplicationTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() {
        String resource = testRestTemplate.getForObject("/readme", String.class);
        Assertions.assertThat(resource)
                .isEqualTo("# qiita-materials\nMaterials for Qiita articles\n");
    }

}
