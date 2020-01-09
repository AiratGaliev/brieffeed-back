package com.brieffeed.back.web;

import com.brieffeed.back.domain.Tag;
import com.brieffeed.back.services.MapValidationErrorService;
import com.brieffeed.back.services.TagService;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin
public class TagController {

  @Autowired
  private TagService tagService;

  @Autowired
  private MapValidationErrorService mapValidationErrorService;

  @GetMapping("")
  public Iterable<Tag> getTags() {
    return tagService.findAll();
  }

  @PostMapping("/create")
  public ResponseEntity<?> create(@Valid @RequestBody Tag tag, BindingResult result,
      Principal principal) {
    ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
    if (errorMap != null) {
      return errorMap;
    }
    Tag blog = tagService.create(tag, principal.getName());
    return new ResponseEntity<>(blog, HttpStatus.CREATED);
  }
}
