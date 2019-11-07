package com.brieffeed.back.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.repositories.BlogRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;

	public Blog create(Blog blog) {
		return blogRepository.save(blog);
	}

	public Blog update(Blog blog, String blogId) {
		Optional<Blog> blogOriginal = blogRepository.findById(Long.valueOf(blogId));
		Blog blog1 = blogOriginal.get();
		blog1.setName(blog.getName());
		blog1.setDescription(blog.getDescription());
		return blogRepository.save(blog1);
	}
}
