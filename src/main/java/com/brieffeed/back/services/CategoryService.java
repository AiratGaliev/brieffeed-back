package com.brieffeed.back.services;

import com.brieffeed.back.domain.Category;
import com.brieffeed.back.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
		return categoryRepository.save(category1);
	}
}
