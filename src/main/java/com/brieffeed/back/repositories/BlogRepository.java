package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long>  {
	Blog findBlogById(Long aLong);

	@Override
	Iterable<Blog> findAllById(Iterable<Long> iterable);

	Iterable<Blog> findAllByAuthor(String username);

	void delete(Blog blog);
}
