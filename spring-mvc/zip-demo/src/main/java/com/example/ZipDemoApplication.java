package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@SpringBootApplication
public class ZipDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipDemoApplication.class, args);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @RequestMapping(path = "/files")
    public void zipDownload(@RequestParam List<MultipartFile> files, HttpServletResponse response) throws IOException {

        response.setHeader(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=download.zip");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            for (MultipartFile file : files) {
                String fileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
                try (InputStream input = file.getInputStream()) {
                    zipOutputStream.putNextEntry(new ZipEntry(fileName));
                    StreamUtils.copy(input, zipOutputStream); // write per 4KB
                }
            }
        }

    }

}
