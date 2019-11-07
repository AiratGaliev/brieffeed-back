package com.brieffeed.back.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

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

@RunWith(SpringRunner.class)
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
		User user = new User("Test2", "Test2", new BCryptPasswordEncoder(12).encode("test2"), Role.AUTHOR, "test2",
				"test2@mail.com", "+2222", "test content", "test city");
		entityManager.persistAndFlush(user);
		Category category = new Category("Test Category");
		categoryRepository.save(category);
		Blog blog = new Blog(category, "Test Blog", "Test Blog Description");
		blogRepository.save(blog);
		Post post = new Post("Test Post", "Test Post Content", blog, user, Status.DRAFT);
		entityManager.persistAndFlush(post);
		assertThat(post.getName()).isNotNull();
	}

	@Test
	public void deletePost() {
		User user = new User("Test2", "Test2", new BCryptPasswordEncoder(12).encode("test3"), Role.AUTHOR, "test3",
				"test3@mail.com", "+3333", "test post content", "test city");
		entityManager.persistAndFlush(user);
		Category category = new Category("Test Category");
		categoryRepository.save(category);
		Blog blog = new Blog(category, "Test Blog", "Test Blog Description");
		blogRepository.save(blog);
		entityManager.persistAndFlush(new Post("Test Post", "Test Post Content", blog, user, Status.DRAFT));
		entityManager.persistAndFlush(new Post("Test Post", "Test Post Content", blog, user, Status.DRAFT));
		postRepository.deleteAll();
		assertThat(postRepository.findAll()).isEmpty();
	}

}
