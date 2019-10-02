package com.brieffeed.back.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.brieffeed.back.domain.Article;
import com.brieffeed.back.domain.Role;
import com.brieffeed.back.domain.User;
import com.brieffeed.back.repository.ArticleRepository;
import com.brieffeed.back.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArticleRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void saveArticle() {
		User user = new User("Test2", "Test2", new BCryptPasswordEncoder(12).encode("test2"), Role.AUTHOR, "test2",
				"test2@mail.com", "+2222", "test description", "test city");
		entityManager.persistAndFlush(user);
		Article article = new Article("Test Article", "test2", "Test Article Description", user);
		entityManager.persistAndFlush(article);
		assertThat(article.getArticleIdentifier()).isNotNull();
	}

	@Test
	public void deleteArticles() {
		User user = new User("Test2", "Test2", new BCryptPasswordEncoder(12).encode("test3"), Role.AUTHOR, "test3",
				"test3@mail.com", "+3333", "test description", "test city");
		entityManager.persistAndFlush(user);
		entityManager.persistAndFlush(new Article("Test Article", "test3", "Test Article Description", user));
		entityManager.persistAndFlush(new Article("Test Article", "test4", "Test Article Description", user));
		articleRepository.deleteAll();
		assertThat(articleRepository.findAll()).isEmpty();
	}

}
