package com.brieffeed.back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogIdException extends RuntimeException {

    public BlogIdException(String message) {
        super(message);
    }
}
