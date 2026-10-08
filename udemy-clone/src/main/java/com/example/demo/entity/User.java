package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public abstract class User extends IdClass{

	@Column(unique = true)
	private String username;
	private String password;
	private String email;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = 
			 new HashSet<>();
	
	public void addRole(Role role) {
		roles.add(role);
	}
	
	public User(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
}
