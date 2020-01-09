package com.brieffeed.back.repository;

import com.brieffeed.back.domain.*;
import com.brieffeed.back.repositories.BlogRepository;
import com.brieffeed.back.repositories.CategoryRepository;
import com.brieffeed.back.repositories.PostRepository;
import com.brieffeed.back.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PostRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private BlogRepository blogRepository;

  @Test
  public void savePost() {
    User user = new User("Test2", "Test2", new BCryptPasswordEncoder(12).encode("test2"),
        Role.AUTHOR.getRole(), "test2",
        "test2@mail.com", "+2222", "test content", "test city");
    userRepository.save(user);
    entityManager.persistAndFlush(user);
    Category category = new Category("Test Category");
    categoryRepository.save(category);
    Blog blog = new Blog(category, "Test Blog", user, "Test Blog Description", user.getUsername());
    blogRepository.save(blog);
    Post post = new Post("Test Post", "Test Post Content", blog, user, user.getUsername(),
        Status.DRAFT.getStatus());
    entityManager.persistAndFlush(post);
    assertThat(post.getTitle()).isNotNull();
  }

  @Test
  public void deletePost() {
    User user = new User("Test2", "Test2", new BCryptPasswordEncoder(12).encode("test3"),
        Role.AUTHOR.getRole(), "test3",
        "test3@mail.com", "+3333", "test post content", "test city");
    entityManager.persistAndFlush(user);
    Category category = new Category("Test Category");
    categoryRepository.save(category);
    Blog blog = new Blog(category, "Test Blog", user, "Test Blog Description", user.getUsername());
    blogRepository.save(blog);
    entityManager.persistAndFlush(
        new Post("Test Post", "Test Post Content", blog, user, user.getUsername(),
            Status.DRAFT.getStatus()));
    entityManager.persistAndFlush(
        new Post("Test Post", "Test Post Content", blog, user, user.getUsername(),
            Status.DRAFT.getStatus()));
    postRepository.deleteAll();
    assertThat(postRepository.findAll()).isEmpty();
  }

}
