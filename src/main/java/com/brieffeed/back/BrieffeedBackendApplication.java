package com.brieffeed.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.brieffeed.back.domain.Article;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.repository.ArticleRepository;
import com.brieffeed.back.repository.UserRepository;

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
			User user = new User("Test", "Test", new BCryptPasswordEncoder(12).encode("test"), Role.AUTHOR, "test", "test@mail.com", "+1111", "test description",
					"test city");
			userRepository.save(user);
			articleRepository.save(new Article("Test Article", "test", "Test Article Description", user));

		};
	}

}