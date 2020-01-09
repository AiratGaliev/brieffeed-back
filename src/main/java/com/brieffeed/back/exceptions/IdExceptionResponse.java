package com.brieffeed.back.exceptions;

public class IdExceptionResponse {

  private String id;

  public IdExceptionResponse(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
