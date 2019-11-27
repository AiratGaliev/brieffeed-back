package com.brieffeed.back.exceptions;

public class PostIdExceptionResponse {
    private String postId;

    public PostIdExceptionResponse(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
