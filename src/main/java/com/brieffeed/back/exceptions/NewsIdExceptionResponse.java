package com.brieffeed.back.exceptions;

public class NewsIdExceptionResponse {
    private String blogId;

    public NewsIdExceptionResponse(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
