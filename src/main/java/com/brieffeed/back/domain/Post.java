package com.brieffeed.back.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import org.springframework.data.repository.NoRepositoryBean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	@NotBlank(message = "Post name is required")
	private String postName;
	@NotBlank(message = "Post description is required")
	private String description;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date createdDate, updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_entity")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private User user;

	public Post() {

	}

	public Post(String postName, String description, User user) {
		this.postName = postName;
		this.description = description;
		this.user = user;
	}

	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedDate = new Date();
	}

	public String getPostName() {
		return postName;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public User getUser() {
		return user;
	}

}
