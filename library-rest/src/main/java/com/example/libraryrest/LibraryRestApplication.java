package com.example.libraryrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class LibraryRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryRestApplication.class, args);
	}

}
