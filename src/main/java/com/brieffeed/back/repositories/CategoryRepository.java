package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Category;
import com.brieffeed.back.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findCategoriesById(Long aLong);

    Iterable<Category> findAll();

    @Override
    Iterable<Category> findAllById(Iterable<Long> iterable);
}
