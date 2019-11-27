package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	@Override
	Iterable<Category> findAllById(Iterable<Long> iterable);
}
