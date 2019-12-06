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
            User user = new User("Richard", "Miles", bCryptPasswordEncoder().encode("user_password"), Role.USER.getRole(), "user",
                    "richard.miles@example.com", "(764)-139-8260", "user test description", "test city");
            User author = new User("Noah", "Gilbert", bCryptPasswordEncoder().encode("author_password"), Role.AUTHOR.getRole(), "author",
                    "noah.gilbert@example.com", "(131)-513-4407", "author test description", "test city");
            User admin = new User("Ricky", "Mitchell", bCryptPasswordEncoder().encode("admin_password"), Role.ADMIN.getRole(), "admin",
                    "ricky.mitchell@example.com", "(207)-785-2184", "admin test description", "test city");
            userRepository.save(user);
            userRepository.save(author);
            userRepository.save(admin);
            Category category1 = new Category("Test Category 1");
            Category category2 = new Category("Test Category 2");
            Category category3 = new Category("Test Category 3");
            Category category4 = new Category("Test Category 4");
            Category category5 = new Category("Test Category 5");
            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);
            categoryRepository.save(category4);
            categoryRepository.save(category5);
            Blog blog1 = new Blog(category1, "Test Blog 1", author, "Test Blog Description 1", author.getUsername());
            Blog blog2 = new Blog(category2, "Test Blog 2", author, "Test Blog Description 2", author.getUsername());
            Blog blog3 = new Blog(category3, "Test Blog 3", author, "Test Blog Description 3", author.getUsername());
            Blog blog4 = new Blog(category4, "Test Blog 4", author, "Test Blog Description 4", author.getUsername());
            Blog blog5 = new Blog(category5, "Test Blog 5", author, "Test Blog Description 5", author.getUsername());
            blogRepository.save(blog1);
            blogRepository.save(blog2);
            blogRepository.save(blog3);
            blogRepository.save(blog4);
            blogRepository.save(blog5);
            postRepository.save(new Post("Test Post 1", "Test Post Content 1", blog1, author, author.getUsername(), Status.PUBLISH.getStatus()));
            postRepository.save(new Post("Test Post 2", "Test Post Content 2", blog2, author, author.getUsername(), Status.PUBLISH.getStatus()));
            postRepository.save(new Post("Test Post 3", "Test Post Content 3", blog3, author, author.getUsername(), Status.PUBLISH.getStatus()));
            postRepository.save(new Post("Test Post 4", "Test Post Content 4", blog4, author, author.getUsername(), Status.PUBLISH.getStatus()));
            postRepository.save(new Post("Test Post 4", "Test Post Content 4", blog5, author, author.getUsername(), Status.PUBLISH.getStatus()));
        };
    }

}
