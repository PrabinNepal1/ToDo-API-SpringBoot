package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;


@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum Role {
		ROLE_USER,
		ROLE_ADMIN
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String name;
	
	@Column(unique=true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(columnDefinition = "boolean default false")
	private boolean enabled;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@OneToMany(mappedBy = "user", targetEntity= ToDos.class,  cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<ToDos> todos = new HashSet<>();

	public User() {
		this(-1,"N/A", "N/A", "N/A", false, Role.ROLE_USER);
	}
	
	
	public User(Integer id, @NotBlank String name, String username, 
					String password, boolean enabled, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}
	
	public void attachToDos() {
		for (ToDos todo : todos) {
			todo.setUser(this);
		}
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public Role getRole() {
		return role;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}


	public Set<ToDos> getTodos() {
		return todos;
	}


	public void setTodos(Set<ToDos> todos) {
		this.todos = todos;
	}
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", enabled="
				+ enabled + ", role=" + role + ", todos=" + todos + "]";
	}

}
