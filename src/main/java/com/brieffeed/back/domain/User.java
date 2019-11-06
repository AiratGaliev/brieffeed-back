package com.brieffeed.back.domain;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_entity")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	private String firstName, lastName, password;
	@Column(nullable = false)
	private Role role;
	@Column(nullable = false, unique = true)
	private String userName, email, phone;
	private String description, city;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Post> posts;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Comment> comments;

	public User() {
	}

	public User(String firstName, String lastName, String password, Role role, String userName, String email,
			String phone, String description, String city) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
		this.userName = userName;
		this.email = email;
		this.phone = phone;
		this.description = description;
		this.city = city;
	}

	public Long getUserId() {
		return id;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getDescription() {
		return description;
	}

	public String getCity() {
		return city;
	}

	public Role getRole() {
		return role;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}
