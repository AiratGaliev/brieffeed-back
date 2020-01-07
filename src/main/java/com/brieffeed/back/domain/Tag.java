package com.brieffeed.back.domain;

import javax.persistence.Entity;

@Entity
public class Tag extends AbstractEntity {
	private String name;

	public Tag(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
