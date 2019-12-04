package com.brieffeed.back.exceptions;

public class NewsNotFoundExceptionResponse {
    private String newsNotFound;

    public NewsNotFoundExceptionResponse(String newsNotFound) {
        this.newsNotFound = newsNotFound;
    }

    public String getNewsNotFound() {
        return newsNotFound;
    }

    public void setNewsNotFound(String newsNotFound) {
        this.newsNotFound = newsNotFound;
    }
}
