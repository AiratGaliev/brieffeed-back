package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long>  {
	@Override
	Iterable<Blog> findAllById(Iterable<Long> iterable);
}
