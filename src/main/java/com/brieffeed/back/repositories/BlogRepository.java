package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Blog;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long> {
    Iterable<Blog> findAll(Sort sort);

    Blog findBlogById(Long aLong);

    @Override
    Iterable<Blog> findAllById(Iterable<Long> iterable);

    Iterable<Blog> findAllByAuthor(String username);

    void delete(Blog blog);
}
