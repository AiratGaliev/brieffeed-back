package com.brieffeed.back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<Object> handleCategoryIdException(CategoryIdException ex, WebRequest request) {
        CategoryIdExceptionResponse exceptionResponse = new CategoryIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest request) {
        CategoryNotFoundExceptionResponse exceptionResponse = new CategoryNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleBlogIdException(BlogIdException ex, WebRequest request) {
        BlogIdExceptionResponse exceptionResponse = new BlogIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleBlogNotFoundException(BlogNotFoundException ex, WebRequest request) {
        BlogNotFoundExceptionResponse exceptionResponse = new BlogNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handlePostIdException(PostIdException ex, WebRequest request) {
        PostIdExceptionResponse exceptionResponse = new PostIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handlePostNotFoundException(PostNotFoundException ex, WebRequest request) {
        PostNotFoundExceptionResponse exceptionResponse = new PostNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNewsIdException(NewsIdException ex, WebRequest request) {
        NewsIdExceptionResponse exceptionResponse = new NewsIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNewsNotFoundException(NewsNotFoundException ex, WebRequest request) {
        NewsNotFoundExceptionResponse exceptionResponse = new NewsNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> usernameAlreadyExists(UsernameAlreadyExistsException ex, WebRequest request) {
        UsernameAlreadyExistsResponse exceptionResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
