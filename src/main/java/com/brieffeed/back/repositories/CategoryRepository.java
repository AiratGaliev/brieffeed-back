package com.brieffeed.back.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brieffeed.back.domain.Category;
import com.brieffeed.back.domain.Post;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	@Override
	Iterable<Category> findAllById(Iterable<Long> iterable);
}
