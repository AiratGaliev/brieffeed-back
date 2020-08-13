package com.brieffeed.back.web;

import com.brieffeed.back.domain.Post;
import com.brieffeed.back.payload.PostRequest;
import com.brieffeed.back.services.MapValidationErrorService;
import com.brieffeed.back.services.PostService;
import java.security.Principal;
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

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {

  private final PostService postService;
  private final MapValidationErrorService mapValidationErrorService;

  @Autowired
  public PostController(
      PostService postService, MapValidationErrorService mapValidationErrorService) {
    this.postService = postService;
    this.mapValidationErrorService = mapValidationErrorService;
  }

  @GetMapping("")
  public Iterable<Post> getPosts() {
    return postService.findAll();
  }

  @GetMapping(path = "", params = {"principal"})
  public Iterable<Post> getPosts(Principal principal) {
    return postService.findAll(principal.getName());
  }

  @GetMapping("/my")
  public Iterable<Post> getMyPosts(Principal principal) {
    return postService.findAllByAuthor(principal.getName());
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<?> getPostById(@PathVariable String id) {
    Post post = postService.findById(id);
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @GetMapping(path = "/{id}", params = {"principal"})
  public ResponseEntity<?> getPostById(@PathVariable String id, Principal principal) {
    Post post = postService.findById(principal.getName(), id);
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<?> create(@Valid @RequestBody PostRequest postRequest, BindingResult result,
      Principal principal) {
    ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
    if (errorMap != null) {
      return errorMap;
    }
    Post post1 = postService.create(postRequest, principal.getName());
    return new ResponseEntity<>(post1, HttpStatus.CREATED);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@Valid @RequestBody PostRequest postRequest, BindingResult result,
      @PathVariable String id, Principal principal) {
    ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
    if (errorMap != null) {
      return errorMap;
    }
    Post post1 = postService.update(postRequest, id, principal.getName());
    return new ResponseEntity<>(post1, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id, Principal principal) {
    postService.delete(principal.getName(), id);
    return new ResponseEntity<>("Post with ID: " + id + " was deleted", HttpStatus.OK);
  }
}
