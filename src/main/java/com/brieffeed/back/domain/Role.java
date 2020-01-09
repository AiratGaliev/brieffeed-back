package com.brieffeed.back.domain;

public enum Role {
  ADMIN("ADMIN"), AUTHOR("AUTHOR"), USER("USER");

  private String role;

  Role(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}
