package com.brieffeed.back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogIdException extends RuntimeException {

    private static final long serialVersionUID = 7755089690388000966L;

    public BlogIdException(String message) {
        super(message);
    }
}
