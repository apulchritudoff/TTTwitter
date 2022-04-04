package com.tts.techtalenttwitter.Model;

import java.util.Set;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//we are going to store user in a database

//and in order to wire up to the database we have to
//annotate the user with an annotaton from jpa
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	
	// in order for it to work
	//there has to be a defult constuer
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	@Email(message = "Email must be valid")
	@NotEmpty(message = "Email cannot be empty")
	private String email;

	@NotEmpty(message = "Username cannot be empty")
	@Length(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
	@Pattern(regexp = "[^//s]+", message = "User must be alphanumeric")
	private String username;

	@NotEmpty(message = "Password cannot be empty")
	@Length(min = 5, message = "Password must be at least 5 characters")
	private String password;

	@NotEmpty(message = "Firstname Password cannot be empty")
	private String firstName;

	@NotEmpty(message = "Lastname Password cannot be empty")
	private String lastName;
	private int active;
	
	@CreationTimestamp
	private Date createdAt;

	//This user class is going to be mapped to database
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_followers", joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "follower_id"))
	private List<User> followers;

	@ManyToMany(mappedBy = "followers")
	private List<User> following;
	
	// User database
		//id id
		//firstname role
	
	//user role table
	//user_id, role_id
	// 1, 1
	// 1 , 2
	// 2 , 1
}
