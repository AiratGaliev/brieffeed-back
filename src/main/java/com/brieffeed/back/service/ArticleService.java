package com.brieffeed.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brieffeed.back.domain.Article;
import com.brieffeed.back.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	public Article saveOrUpdateArticel(Article article) {
		
		// logic
		return articleRepository.save(article);
	}
}
