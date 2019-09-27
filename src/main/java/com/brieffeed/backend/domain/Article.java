package com.brieffeed.backend.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	public Article() {

	}
	
	

	public Article(String articleName, String articleIdentifier, String description) {
		this.articleName = articleName;
		this.articleIdentifier = articleIdentifier;
		this.description = description;
	}



	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updateDate = new Date();
	}
}
