package com.brieffeed.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brieffeed.backend.domain.Article;
import com.brieffeed.backend.service.ArticleService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@PostMapping("")
	public ResponseEntity<Article> createNewArticle(@RequestBody Article article) {
		articleService.saveOrUpdateArticel(article);
		return new ResponseEntity<Article>(article, HttpStatus.CREATED);
	}
}
