package com.brieffeed.back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostIdException extends RuntimeException {

    private String message;

    public PostIdException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
