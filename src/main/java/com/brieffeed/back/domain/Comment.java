package com.brieffeed.back.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	private String text;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date createdDate, updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_entity", updatable = false
//	, nullable = false
	)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post", updatable = false
//	, nullable = false
	)
	private Post post;

	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public User getUser() {
		return user;
	}

	public Post getPost() {
		return post;
	}

}
