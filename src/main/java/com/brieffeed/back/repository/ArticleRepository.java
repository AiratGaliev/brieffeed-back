package com.brieffeed.back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brieffeed.back.domain.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
	@Override
	Iterable<Article> findAllById(Iterable<Long> iterable);

}
