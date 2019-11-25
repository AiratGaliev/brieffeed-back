package com.brieffeed.back.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brieffeed.back.domain.Post;
import com.brieffeed.back.services.PostService;
import com.brieffeed.back.services.MapValidationErrorService;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Post post, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        Post post1 = postService.create(post, principal.getName());
        return new ResponseEntity<Post>(post1, HttpStatus.CREATED);
    }

    @PatchMapping("/{postId}/update")
    public ResponseEntity<?> update(@Valid @RequestBody Post post, BindingResult result, @PathVariable String postId) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        Post post1 = postService.update(post, postId);
        return new ResponseEntity<>(post1, HttpStatus.OK);
    }
}
