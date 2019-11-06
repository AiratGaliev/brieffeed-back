package com.brieffeed.back.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brieffeed.back.domain.Category;
import com.brieffeed.back.services.CategoryService;
import com.brieffeed.back.services.MapValidationErrorService;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Category category, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
		if (errorMap != null)
			return errorMap;
		Category category1 = categoryService.create(category);
		return new ResponseEntity<>(category1, HttpStatus.CREATED);
	}

	@PatchMapping("/{categoryId}/update")
	public ResponseEntity<?> update(@Valid @RequestBody Category category, BindingResult result,
			@PathVariable String categoryId) {
		ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
		if (errorMap != null)
			return errorMap;
		Category category1 = categoryService.update(category, categoryId);
		return new ResponseEntity<>(category1, HttpStatus.OK);
	}
}
