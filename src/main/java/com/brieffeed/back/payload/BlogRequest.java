package com.brieffeed.back.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BlogRequest {

  @NotBlank(message = "Blog name cannot be blank")
  private String name;
  @NotBlank(message = "Blog description cannot be blank")
  private String description;
  @NotNull(message = "Please select your category")
  private Long categoryId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
}
