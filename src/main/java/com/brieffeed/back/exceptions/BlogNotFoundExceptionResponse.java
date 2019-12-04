package com.brieffeed.back.exceptions;

public class BlogNotFoundExceptionResponse {
    private String blogNotFound;

    public BlogNotFoundExceptionResponse(String blogNotFound) {
        this.blogNotFound = blogNotFound;
    }

    public String getBlogNotFound() {
        return blogNotFound;
    }

    public void setBlogNotFound(String blogNotFound) {
        this.blogNotFound = blogNotFound;
    }
}
