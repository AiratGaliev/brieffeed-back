package com.brieffeed.back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryIdException extends RuntimeException {

    private static final long serialVersionUID = 3935257658125868288L;

    public CategoryIdException(String message) {
        super(message);
    }
}
