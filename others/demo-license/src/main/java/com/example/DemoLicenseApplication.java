package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class DemoLicenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoLicenseApplication.class, args);
	}


	@RequestMapping("/")
	String home() {
		return "home";
	}

}
