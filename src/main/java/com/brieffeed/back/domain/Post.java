package com.brieffeed.back.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "post")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post extends AbstractEntity {

    @Column(nullable = false)
    @NotBlank(message = "Post title is required")
    private String title;
    @Lob
    @Type(type = "text")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date createdDate, updatedDate;
    private String status = Status.DRAFT.getStatus();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_entity_id", updatable = false, nullable = false)
    @JsonIgnore
    private User user;

    private String author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "blog_id", nullable = false)
    @JsonIgnore
    private Blog blog;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();

    public Post() {

    }

    public Post(String title, String content, Blog blog, User user, String author, String status) {
        this.title = title;
        this.content = content;
        this.blog = blog;
        this.user = user;
        this.author = author;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
        if (this.content != null) {
            if (this.content.length() > 600)
                this.description = this.content.substring(0, 600) + "...";
            else {
                this.description = this.content;
            }
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date();
        if (this.content != null) {
            if (this.content.length() > 600)
                this.description = this.content.substring(0, 600) + "...";
            else {
                this.description = this.content;
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Blog getBlog() {
        return blog;
    }

    public Long getBlogId() {
        return blog.getId();
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
