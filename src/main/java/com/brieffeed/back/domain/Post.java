package com.brieffeed.back.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	@NotBlank(message = "Post title is required")
	private String title;
	@Lob
	private String description;
	@Column(columnDefinition = "text")
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date createdDate, updatedDate;
	private Status status = Status.DRAFT;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_entity_id", updatable = false
	// , nullable = false
	)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "blog_id"
	// , nullable = false
	)
	private Blog blog;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public Post() {

	}

	public Post(String title, String content, Blog blog, User user, Status status) {
		this.title = title;
		this.content = content;
		this.blog = blog;
		this.user = user;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public Long getPostId() {
		return id;
	}

}
