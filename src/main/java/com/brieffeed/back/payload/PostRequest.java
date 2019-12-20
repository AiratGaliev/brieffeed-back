package com.brieffeed.back.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PostRequest {
    @NotBlank(message = "Post title is required")
    private String title;
    private String content;
    private String status;
    @NotNull(message = "Please select your blog")
    private Long blogId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}
