package com.brieffeed.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Blog extends AbstractEntity {

    @Column(nullable = false)
    @NotBlank(message = "Blog name is required")
    private String name;
    @NotBlank(message = "Blog description is required")
    private String description;

    @Lob
    private Byte[] image;

    private String author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_entity_id", updatable = false, nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blog", orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    public Blog() {
    }

    public Blog(Category category, String name, User user, String description, String author) {
        this.category = category;
        this.name = name;
        this.user = user;
        this.description = description;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Category getCategory() {
        return category;
    }

    public Long getCategoryId() {
        return category.getId();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
