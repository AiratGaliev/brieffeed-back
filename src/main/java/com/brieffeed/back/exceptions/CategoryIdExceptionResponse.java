package com.brieffeed.back.exceptions;

public class CategoryIdExceptionResponse {
    private String categoryId;

    public CategoryIdExceptionResponse(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
