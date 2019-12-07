package com.brieffeed.back.web;

import com.brieffeed.back.domain.Blog;
import com.brieffeed.back.domain.Category;
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
    public Iterable<Blog> getCategories() {
        return blogService.findAll();
    }

    @GetMapping("/my")
    public Iterable<Blog> getMyPosts(Principal principal) {
        return blogService.findAllByAuthor(principal.getName());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Blog blog, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        Blog blog1 = blogService.create(blog, principal.getName());
        return new ResponseEntity<>(blog1, HttpStatus.CREATED);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<?> getPostById(@PathVariable String blogId) {
        Blog blog = blogService.findById(blogId);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @PatchMapping("/{blogId}")
    public ResponseEntity<?> update(@Valid @RequestBody Blog blog, BindingResult result,
                                    @PathVariable String blogId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        Blog blog1 = blogService.update(blog, blogId, principal.getName());
        return new ResponseEntity<>(blog1, HttpStatus.OK);
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<?> delete(@PathVariable String blogId, Principal principal) {
        blogService.delete(principal.getName(), blogId);
        return new ResponseEntity<>("Blog with ID: " + blogId + " was deleted", HttpStatus.OK);
    }
}
