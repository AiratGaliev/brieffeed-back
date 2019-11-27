package com.brieffeed.back.web;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.services.BlogService;
import com.brieffeed.back.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin
public class BlogController {
	@Autowired
	private BlogService blogService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Blog blog, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
		if (errorMap != null)
			return errorMap;
		Blog blog1 = blogService.create(blog);
		return new ResponseEntity<>(blog1, HttpStatus.CREATED);
	}

	@PatchMapping("/{blogId}/update")
	public ResponseEntity<?> update(@Valid @RequestBody Blog blog, BindingResult result,
			@PathVariable String blogId) {
		ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
		if (errorMap != null)
			return errorMap;
		Blog blog1 = blogService.update(blog, blogId);
		return new ResponseEntity<>(blog1, HttpStatus.OK);
	}
}
