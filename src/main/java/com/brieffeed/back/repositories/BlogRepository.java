package com.brieffeed.back.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brieffeed.back.domain.Blog;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long>  {
	@Override
	Iterable<Blog> findAllById(Iterable<Long> iterable);
}
