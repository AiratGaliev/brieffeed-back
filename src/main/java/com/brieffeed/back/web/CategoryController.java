package com.brieffeed.back.web;

import com.brieffeed.back.domain.Category;
import com.brieffeed.back.domain.Post;
import com.brieffeed.back.services.CategoryService;
import com.brieffeed.back.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@GetMapping("")
	public Iterable<Category> getCategories() {
		return categoryService.findAll();
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Category category, BindingResult result, Principal principal) {
		ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
		if (errorMap != null)
			return errorMap;
		Category category1 = categoryService.create(category, principal.getName());
		return new ResponseEntity<>(category1, HttpStatus.CREATED);
	}

	@PatchMapping("/{categoryId}")
	public ResponseEntity<?> update(@Valid @RequestBody Category category, BindingResult result,
									@PathVariable String categoryId, Principal principal) {
		ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
		if (errorMap != null)
			return errorMap;
		Category category1 = categoryService.update(category, categoryId, principal.getName());
		return new ResponseEntity<>(category1, HttpStatus.OK);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> delete(@PathVariable String categoryId, Principal principal) {
		categoryService.delete(principal.getName(), categoryId);
		return new ResponseEntity<>("Category with ID: " + categoryId + " was deleted", HttpStatus.OK);
	}
}
