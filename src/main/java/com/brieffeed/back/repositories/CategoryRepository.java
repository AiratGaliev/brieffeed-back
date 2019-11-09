package com.brieffeed.back.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brieffeed.back.domain.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	@Override
	Iterable<Category> findAllById(Iterable<Long> iterable);
}
