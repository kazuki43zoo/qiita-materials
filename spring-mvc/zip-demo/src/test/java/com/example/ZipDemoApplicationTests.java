package com.example;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZipDemoApplication.class)
@WebIntegrationTest(randomPort = true)
public class ZipDemoApplicationTests {

    @Value("http://localhost:${local.server.port}/files")
    private String url;

    @Test
    public void zipDownloadUsingSpringRestTemplate() throws IOException {

        RestTemplate restOperations = new RestTemplate();
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("files", new FileSystemResource("data/a.txt"));
        form.add("files", new FileSystemResource("data/b.txt"));
        RequestEntity<MultiValueMap<String, Object>> requestEntity =
                RequestEntity.post(URI.create(url)).body(form);

        // ★★★ この実装だとByteArrayInputStreamとして扱われるので大量データを扱う場合は注意が必要！！ ★★★
        ResponseEntity<Resource> responseEntity = restOperations.exchange(requestEntity, Resource.class);

        try (ZipInputStream zipInputStream = new ZipInputStream(responseEntity.getBody().getInputStream())) {
            {
                ZipEntry entry = zipInputStream.getNextEntry();
                assertThat(entry.getName(), is("a.txt"));
                assertThat(StreamUtils.copyToString(zipInputStream, StandardCharsets.UTF_8), is("aaaaa"));
            }
            {
                ZipEntry entry = zipInputStream.getNextEntry();
                assertThat(entry.getName(), is("b.txt"));
                assertThat(StreamUtils.copyToString(zipInputStream, StandardCharsets.UTF_8), is("bbbbb"));
            }
            Assert.assertNull(zipInputStream.getNextEntry());
        }
    }


    @Test
    public void zipDownloadUsingApacheHttpClient() throws IOException {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpPost request = new HttpPost(url);
            request.setEntity(MultipartEntityBuilder.create()
                    .addPart("files", new FileBody(new File("data/a.txt")))
                    .addPart("files", new FileBody(new File("data/b.txt")))
                    .build());

            HttpResponse response = httpClient.execute(request);

            try (ZipInputStream zipInputStream = new ZipInputStream(response.getEntity().getContent())) {
                {
                    ZipEntry entry = zipInputStream.getNextEntry();
                    assertThat(entry.getName(), is("a.txt"));
                    assertThat(StreamUtils.copyToString(zipInputStream, StandardCharsets.UTF_8), is("aaaaa"));
                }
                {
                    ZipEntry entry = zipInputStream.getNextEntry();
                    assertThat(entry.getName(), is("b.txt"));
                    assertThat(StreamUtils.copyToString(zipInputStream, StandardCharsets.UTF_8), is("bbbbb"));
                }
                Assert.assertNull(zipInputStream.getNextEntry());
            }

        }

    }

}
