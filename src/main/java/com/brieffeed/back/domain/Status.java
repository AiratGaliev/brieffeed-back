package com.brieffeed.back.domain;

public enum Status {
  PUBLISH("PUBLISH"), DRAFT("DRAFT"), ARCHIVE("ARCHIVE");

  private String status;

  Status(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
