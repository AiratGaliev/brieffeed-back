package com.brieffeed.backend.domain;

import javax.persistence.*;

@Entity(name = "user_entity")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	@Column(nullable = false)
	private String firstName, lastName, password, role;
	@Column(nullable = false, unique = true)
	private String userName, email, phone;
	private String description, city;

	public User() {
	}

	public User(String firstName, String lastName, String password, String role, String userName, String email,
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
	
	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}
}