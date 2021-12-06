package com.example.springbackoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@SpringBootApplication
public class SpringBackOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBackOfficeApplication.class, args);
	}

}
