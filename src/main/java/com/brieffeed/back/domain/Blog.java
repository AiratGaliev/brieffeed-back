package com.brieffeed.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	@NotBlank(message = "Blog name is required")
	private String name;
	@NotBlank(message = "Blog description is required")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "category_id"
//	, nullable = false
	)
	private Category category;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "blog", orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();

	public Blog() {
	}

	public Blog(Category category, String name, String description) {
		this.category = category;
		this.name = name;
		this.description = description;
	}

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

	public List<Post> getPosts() {
		return posts;
	}

	public Long getBlogId() {
		return id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
