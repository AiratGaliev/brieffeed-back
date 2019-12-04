package com.brieffeed.back.repositories;

import com.brieffeed.back.domain.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {
    News findNewsById(Long aLong);

    Iterable<News> findAll();

    @Override
    Iterable<News> findAllById(Iterable<Long> iterable);

    Iterable<News> findAllByAuthor(String username);

    Iterable<News> findAllByAuthorAndStatus(String username, String status);

    Iterable<News> findAllByStatus(String status);

    void delete(News post);
}
