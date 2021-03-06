package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

  Post findPostById(Long aLong);

  Iterable<Post> findAll(Sort sort);

  @Override
  Iterable<Post> findAllById(Iterable<Long> iterable);

  Iterable<Post> findAllByAuthor(String username);

  Iterable<Post> findAllByAuthorAndStatus(String username, String status);

  Iterable<Post> findAllByStatus(String status, Sort sort);

  void delete(Post post);
}
