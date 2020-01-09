package com.brieffeed.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tag extends AbstractEntity {

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "tags")
  @JsonIgnore
  private Set<Post> posts = new HashSet<>();

  public Tag() {
  }

  public Tag(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Post> getPosts() {
    return posts;
  }

  public void setPosts(Set<Post> posts) {
    this.posts = posts;
  }
}
