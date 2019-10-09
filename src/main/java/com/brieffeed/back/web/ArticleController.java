package com.brieffeed.back.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brieffeed.back.domain.Article;
import com.brieffeed.back.services.ArticleService;
import com.brieffeed.back.services.MapValidationErrorService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewArticle(@Valid @RequestBody Article article, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
		if (errorMap != null)
			return errorMap;
		Article article1 = articleService.saveOrUpdateArticel(article);
		return new ResponseEntity<>(article1, HttpStatus.CREATED);
	}
}
