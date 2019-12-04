package com.brieffeed.back.web;

import com.brieffeed.back.domain.News;
import com.brieffeed.back.services.MapValidationErrorService;
import com.brieffeed.back.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/news")
@CrossOrigin
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("")
    public Iterable<News> getPosts() {
        return newsService.findAll();
    }

    @GetMapping(path = "", params = {"principal"})
    public Iterable<News> getPosts(Principal principal) {
        return newsService.findAll(principal.getName());
    }

    @GetMapping("/my")
    public Iterable<News> getMyPosts(Principal principal) {
        return newsService.findAllByAuthor(principal.getName());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody News news, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        News news1 = newsService.create(news, principal.getName());
        return new ResponseEntity<>(news1, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{newsId}")
    public ResponseEntity<?> getPostById(@PathVariable String newsId) {
        News news = newsService.findById(newsId);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @GetMapping(path = "/{newsId}", params = {"principal"})
    public ResponseEntity<?> getPostById(@PathVariable String newsId, Principal principal) {
        News news = newsService.findById(principal.getName(), newsId);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @PatchMapping("/{newsId}")
    public ResponseEntity<?> update(@Valid @RequestBody News news, BindingResult result, @PathVariable String newsId, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
        if (errorMap != null)
            return errorMap;
        News news1 = newsService.update(news, newsId, principal.getName());
        return new ResponseEntity<>(news1, HttpStatus.OK);
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<?> delete(@PathVariable String newsId, Principal principal) {
        newsService.delete(principal.getName(), newsId);
        return new ResponseEntity<>("News with ID: " + newsId + " was deleted", HttpStatus.OK);
    }
}
