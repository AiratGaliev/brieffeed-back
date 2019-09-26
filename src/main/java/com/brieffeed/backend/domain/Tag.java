package com.brieffeed.backend.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tagId;
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "post_tag", joinColumns = {@JoinColumn(name =
            "tagId")}, inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<Article> posts = new HashSet<>(0);

    public Tag() {
    }
}
