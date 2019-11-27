package com.brieffeed.back.web;

import javax.validation.Valid;

import com.brieffeed.back.domain.User;
import com.brieffeed.back.exceptions.PostIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public Iterable<Post> getPosts(Principal principal) {
        return postService.findAllByAuthorAndStatus(principal.getName());
    }

    @GetMapping("/all")
    public Iterable<Post> getAllPosts(Principal principal) {
        return postService.findAll(principal.getName());
    }

    @GetMapping("/my")
    public Iterable<Post> getMyPosts(Principal principal) {
        return postService.findAllByAuthor(principal.getName());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Post post, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        Post post1 = postService.create(post, principal.getName());
        return new ResponseEntity<>(post1, HttpStatus.CREATED);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable String postId, Principal principal) {
        Post post = postService.findById(principal.getName(), postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PatchMapping("/{postId}/update")
    public ResponseEntity<?> update(@Valid @RequestBody Post post, BindingResult result, @PathVariable String postId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        Post post1 = postService.update(post, postId, principal.getName());
        return new ResponseEntity<>(post1, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable String postId, Principal principal) {
        postService.delete(principal.getName(), postId);
        return new ResponseEntity<>("Post with ID: " + postId + " was deleted", HttpStatus.OK);
    }
}
