package com.brieffeed.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.domain.Category;
import com.brieffeed.back.domain.Post;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.Status;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.repositories.BlogRepository;
import com.brieffeed.back.repositories.CategoryRepository;
import com.brieffeed.back.repositories.PostRepository;
import com.brieffeed.back.repositories.UserRepository;

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
            User user = new User("Test", "Test", new BCryptPasswordEncoder().encode("password"), Role.AUTHOR, "test",
                    "test@mail.com", "+1111", "user test description", "test city");
            userRepository.save(user);
            Category category = new Category("Test Category");
            categoryRepository.save(category);
            Blog blog = new Blog(category, "Test Blog", "Test Blog Description");
            blogRepository.save(blog);
            postRepository.save(new Post("Test Post 1", "Test Post Content 1", blog, user, Status.DRAFT));
            postRepository.save(new Post("Test Post 2", "Test Post Content 2", blog, user, Status.DRAFT));

        };
    }

}
