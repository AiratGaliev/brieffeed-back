package com.brieffeed.back.web;

import com.brieffeed.back.domain.Image;
import com.brieffeed.back.payload.ImageRequest;
import com.brieffeed.back.services.ImageService;
import com.brieffeed.back.services.MapValidationErrorService;
import java.io.IOException;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
@CrossOrigin
public class ImageController {

  private final ImageService imageService;
  private final MapValidationErrorService mapValidationErrorService;

  @Autowired
  public ImageController(ImageService imageService,
      MapValidationErrorService mapValidationErrorService) {
    this.imageService = imageService;
    this.mapValidationErrorService = mapValidationErrorService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getImageById(@PathVariable String id) {
    Image image = imageService.findById(id);
    return new ResponseEntity<>(image, HttpStatus.OK);
  }

  @PostMapping("/upload")
  public ResponseEntity<?> create(@Valid @RequestBody ImageRequest imageRequest,
      BindingResult result, Principal principal) throws IOException {
    ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
    if (errorMap != null) {
      return errorMap;
    }
    Image image = imageService.upload(imageRequest, principal.getName());
    return new ResponseEntity<>(image, HttpStatus.CREATED);
  }
}
