package com.brieffeed.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.brieffeed.backend.domain.Article;
import com.brieffeed.backend.domain.User;
import com.brieffeed.backend.repository.ArticleRepository;
import com.brieffeed.backend.repository.UserRepository;

@SpringBootApplication
public class BrieffeedBackendApplication {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BrieffeedBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			User user = new User("Test", "Test", "test", "author", "test", "test@mail.com", "+1111", "test description",
					"test city");
			userRepository.save(user);
			articleRepository.save(new Article("Test Article", "test", "Test Article Description", user));

		};
	}

}
