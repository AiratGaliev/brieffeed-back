package com.brieffeed.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brieffeed.back.domain.Post;
import com.brieffeed.back.repositories.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;

	public Post create(Post newPost) {
		return postRepository.save(newPost);

	}

	public Post update(Post updatedPost, String postId) {
		Post originalPost = postRepository.findById(Long.valueOf(postId)).get();
		Post post1 = originalPost;
		post1.setName(updatedPost.getName());
		post1.setContent(updatedPost.getContent());
		post1.setStatus(updatedPost.getStatus());
		return postRepository.save(post1);
	}
}
