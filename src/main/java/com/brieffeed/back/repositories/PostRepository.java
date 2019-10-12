package com.brieffeed.back.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brieffeed.back.domain.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	@Override
	Iterable<Post> findAllById(Iterable<Long> iterable);

}
