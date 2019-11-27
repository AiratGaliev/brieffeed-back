package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brieffeed.back.domain.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Post findPostById(Long aLong);

    Iterable<Post> findAll();

    @Override
    Iterable<Post> findAllById(Iterable<Long> iterable);

    Iterable<Post> findAllByAuthor(String username);

    Iterable<Post> findAllByAuthorAndStatus(String username, String status);

    Iterable<Post> findAllByStatus(String status);

    void delete(Post post);
}
