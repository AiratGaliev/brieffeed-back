package com.brieffeed.back.web;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.payload.BlogRequest;
import com.brieffeed.back.services.BlogService;
import com.brieffeed.back.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("")
    public Iterable<Blog> getBlogs() {
        return blogService.findAll();
    }

    @GetMapping("/my")
    public Iterable<Blog> getMyBlogs(Principal principal) {
        return blogService.findAllByAuthor(principal.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable String id) {
        Blog blog = blogService.findById(id);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody BlogRequest blogRequest, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        Blog blog = blogService.create(blogRequest, principal.getName());
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Blog blog, BindingResult result,
                                    @PathVariable String id, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        Blog blog1 = blogService.update(blog, id, principal.getName());
        return new ResponseEntity<>(blog1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id, Principal principal) {
        blogService.delete(principal.getName(), id);
        return new ResponseEntity<>("Blog with ID: " + id + " was deleted", HttpStatus.OK);
    }
}
