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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	private String articleName;
	@Column(nullable = false)
	private String description;
	private Date startDate;
	private Date endDate;

	private Date createdDate;
	private Date updateDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_entity")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private User user;

	public Article() {

	}

	public Article(String articleName, String description, User user) {
		this.articleName = articleName;
		this.description = description;
		if (user.getRole() == Role.AUTHOR) {
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

	public String getArticleName() {
		return articleName;
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
