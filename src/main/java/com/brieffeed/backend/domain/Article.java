package com.brieffeed.backend.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String articleName;
	private String articleIdentifier;
	private String description;
	private Date startDate;
	private Date endDate;

	private Date createdDate;
	private Date updateDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_entity")
	private User user;

	public Article() {

	}

	public Article(String articleName, String articleIdentifier, String description, User user) {
		this.articleName = articleName;
		this.articleIdentifier = articleIdentifier;
		this.description = description;
		if (user.getRole() == "author") {
			this.user = user;
		}
	}

	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updateDate = new Date();
	}

	public User getUser() {
		return user;
	}

}
