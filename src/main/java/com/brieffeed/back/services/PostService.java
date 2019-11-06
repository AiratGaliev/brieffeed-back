package com.brieffeed.back.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brieffeed.back.domain.Post;
import com.brieffeed.back.repositories.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;


	public Post create(Post post) {
		return postRepository.save(post);

	}

	public Post update(Post post, String postId) {
		Optional<Post> postOriginal = postRepository.findById(Long.valueOf(postId));
		Post post1 = postOriginal.get();
		post1.setName(post.getName());
		post1.setContent(post.getContent());
		post1.setStatus(post.getStatus());
		return postRepository.save(post1);
	}
}
