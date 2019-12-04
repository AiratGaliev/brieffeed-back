package com.brieffeed.back.exceptions;

public class PostNotFoundExceptionResponse {
    private String postNotFound;

    public PostNotFoundExceptionResponse(String postNotFound) {
        this.postNotFound = postNotFound;
    }

    public String getPostNotFound() {
        return postNotFound;
    }

    public void setPostNotFound(String postNotFound) {
        this.postNotFound = postNotFound;
    }
}
