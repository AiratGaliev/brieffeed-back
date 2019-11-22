package com.brieffeed.back;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.brieffeed.back.web.PostController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BrieffeedBackendApplicationTest {
	@Autowired
	private PostController postController;

	@Test
	public void contextLoads() {
		assertThat(postController).isNotNull();
	}

}
