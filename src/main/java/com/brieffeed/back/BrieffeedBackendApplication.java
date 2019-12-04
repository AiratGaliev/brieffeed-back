package com.brieffeed.back;

import com.brieffeed.back.domain.*;
import com.brieffeed.back.repositories.BlogRepository;
import com.brieffeed.back.repositories.CategoryRepository;
import com.brieffeed.back.repositories.PostRepository;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BrieffeedBackendApplication {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BlogRepository blogRepository;

    public static void main(String[] args) {
        SpringApplication.run(BrieffeedBackendApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            User user = new User("Test", "Test", bCryptPasswordEncoder().encode("password"), Role.AUTHOR.getRole(), "test",
                    "test@mail.com", "+1111", "user test description", "test city");
            userRepository.save(user);
            Category category = new Category("Test Category");
            categoryRepository.save(category);
            Blog blog = new Blog(category, "Test Blog", user, "Test Blog Description", user.getUsername());
            blogRepository.save(blog);
            postRepository.save(new Post("Test Post 1", "Test Post Content 1", blog, user, user.getUsername(), Status.DRAFT.getStatus()));
            postRepository.save(new Post("Test Post 2", "Test Post Content 2", blog, user, user.getUsername(), Status.DRAFT.getStatus()));
            postRepository.save(new Post("Test Post 3", "Test Post Content 2", blog, user, user.getUsername(), Status.PUBLISH.getStatus()));

        };
    }

}
