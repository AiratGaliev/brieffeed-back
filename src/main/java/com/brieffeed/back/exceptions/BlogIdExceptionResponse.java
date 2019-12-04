package com.brieffeed.back.exceptions;

public class BlogIdExceptionResponse {
    private String blogId;

    public BlogIdExceptionResponse(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
