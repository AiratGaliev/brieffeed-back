package com.brieffeed.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.brieffeed.backend.domain.Article;
import com.brieffeed.backend.repository.ArticleRepository;

@SpringBootApplication
public class BrieffeedBackendApplication {

	@Autowired
	private ArticleRepository articleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BrieffeedBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			articleRepository.save(new Article("Test Article", "test", "Test Article Description"));

		};
	}

}
