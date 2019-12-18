package com.brieffeed.back.web;

import com.brieffeed.back.domain.Category;
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

	@GetMapping("/{id}")
	public ResponseEntity<?> getPostById(@PathVariable String id) {
		Category category = categoryService.findById(id);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Category category, BindingResult result,
									@PathVariable String id, Principal principal) {
		ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
		if (errorMap != null)
			return errorMap;
		Category category1 = categoryService.update(category, id, principal.getName());
		return new ResponseEntity<>(category1, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id, Principal principal) {
		categoryService.delete(principal.getName(), id);
		return new ResponseEntity<>("Category with ID: " + id + " was deleted", HttpStatus.OK);
	}
}
