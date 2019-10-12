package com.brieffeed.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brieffeed.back.domain.Post;
import com.brieffeed.back.repositories.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;

	public Post saveOrUpdateArticel(Post post) {

		// logic
		return postRepository.save(post); 

	}
}
