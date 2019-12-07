package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findCategoryById(Long aLong);

    Iterable<Category> findAll(Sort sort);

    @Override
    Iterable<Category> findAllById(Iterable<Long> iterable);

    void delete(Category category);
}
