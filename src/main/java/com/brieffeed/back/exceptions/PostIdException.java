package com.brieffeed.back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostIdException extends RuntimeException {

    private static final long serialVersionUID = 1215469627220714752L;

    public PostIdException(String message) {
        super(message);
    }
}
