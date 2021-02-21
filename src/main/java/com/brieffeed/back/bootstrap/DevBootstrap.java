package com.brieffeed.back.bootstrap;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.domain.Category;
import com.brieffeed.back.domain.Post;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.Status;
import com.brieffeed.back.domain.Tag;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.repositories.BlogRepository;
import com.brieffeed.back.repositories.CategoryRepository;
import com.brieffeed.back.repositories.PostRepository;
import com.brieffeed.back.repositories.TagRepository;
import com.brieffeed.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final BlogRepository blogRepository;
  private final TagRepository tagRepository;

  @Autowired
  public DevBootstrap(PostRepository postRepository, UserRepository userRepository,
      CategoryRepository categoryRepository, BlogRepository blogRepository,
      TagRepository tagRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
    this.blogRepository = blogRepository;
    this.tagRepository = tagRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    initData();
  }

  private void initData() {

    User user = new User("Richard", "Miles", new BCryptPasswordEncoder().encode("user_password"),
        Role.USER.getRole(), "user",
        "richard.miles@example.com", "(764)-139-8260", "user test description", "test city");
    User author = new User("Noah", "Gilbert", new BCryptPasswordEncoder().encode("author_password"),
        Role.AUTHOR.getRole(), "author",
        "noah.gilbert@example.com", "(131)-513-4407", "author test description", "test city");
    User admin = new User("Ricky", "Mitchell", new BCryptPasswordEncoder().encode("admin_password"),
        Role.ADMIN.getRole(), "admin",
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
    Blog blog1 = new Blog(category1, "Test Blog 1", author, "Test Blog Description 1",
        author.getUsername());
    Blog blog2 = new Blog(category2, "Test Blog 2", author, "Test Blog Description 2",
        author.getUsername());
    Blog blog3 = new Blog(category3, "Test Blog 3", author, "Test Blog Description 3",
        author.getUsername());
    Blog blog4 = new Blog(category4, "Test Blog 4", author, "Test Blog Description 4",
        author.getUsername());
    Blog blog5 = new Blog(category5, "Test Blog 5", author, "Test Blog Description 5",
        author.getUsername());
    blogRepository.save(blog1);
    blogRepository.save(blog2);
    blogRepository.save(blog3);
    blogRepository.save(blog4);
    blogRepository.save(blog5);
    Tag tag1 = new Tag("tag1");
    Tag tag2 = new Tag("tag2");
    Tag tag3 = new Tag("tag3");
    Tag tag4 = new Tag("tag4");
    Tag tag5 = new Tag("tag5");
    Post post1 = new Post("Test Post 1", "Test Post Content 1", blog1, author, author.getUsername(),
        Status.PUBLISH.getStatus());
    Post post2 = new Post("Test Post 2", "Test Post Content 2", blog2, author, author.getUsername(),
        Status.PUBLISH.getStatus());
    Post post3 = new Post("Test Post 3", "Test Post Content 3", blog3, author, author.getUsername(),
        Status.PUBLISH.getStatus());
    Post post4 = new Post("Test Post 4", "Test Post Content 4", blog4, author, author.getUsername(),
        Status.PUBLISH.getStatus());
    Post post5 = new Post("Test Post 5", "Test Post Content 5", blog5, author, author.getUsername(),
        Status.PUBLISH.getStatus());
    post1.getTags().add(tag1);
    postRepository.save(post1);
    postRepository.save(post2);
    postRepository.save(post3);
    postRepository.save(post4);
    postRepository.save(post5);
    tagRepository.save(tag1);
    tagRepository.save(tag2);
    tagRepository.save(tag3);
    tagRepository.save(tag4);
    tagRepository.save(tag5);
  }
}
