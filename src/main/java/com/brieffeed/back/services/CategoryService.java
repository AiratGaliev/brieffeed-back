package com.brieffeed.back.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brieffeed.back.domain.Category;
import com.brieffeed.back.domain.Post;
import com.brieffeed.back.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Category create(Category category) {
		return categoryRepository.save(category);
	}

	public Category update(Category category, String categoryId) {
		Optional<Category> categoryOriginal = categoryRepository.findById(Long.valueOf(categoryId));
		Category category1 = categoryOriginal.get();
		category1.setName(category.getName());
		category1.setDescription(category.getDescription());
		return categoryRepository.save(category1);
	}
}
