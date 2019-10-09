package com.brieffeed.back;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.brieffeed.back.web.ArticleController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrieffeedBackendApplicationTest {
	@Autowired
	private ArticleController articleController;

	@Test
	public void contextLoads() {
		assertThat(articleController).isNotNull();
	}

}
