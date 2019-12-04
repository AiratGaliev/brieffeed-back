package com.brieffeed.back.exceptions;

public class CategoryNotFoundExceptionResponse {
    private String categoryNotFound;

    public CategoryNotFoundExceptionResponse(String categoryNotFound) {
        this.categoryNotFound = categoryNotFound;
    }

    public String getCategoryNotFound() {
        return categoryNotFound;
    }

    public void setCategoryNotFound(String categoryNotFound) {
        this.categoryNotFound = categoryNotFound;
    }
}
