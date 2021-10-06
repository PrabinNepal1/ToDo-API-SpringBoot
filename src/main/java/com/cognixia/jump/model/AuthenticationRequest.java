package com.cognixia.jump.model;

import java.util.HashSet;
import java.util.Set;

import com.cognixia.jump.model.User.Role;

public class AuthenticationRequest {
	
	private String name;
	private String username;
	private String password;
	private Role role;
	private Set<ToDos> todos = new HashSet<>();
	
	public AuthenticationRequest() {}
	
	public AuthenticationRequest(String username) {
		this.username = username;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role=role;
	}
	
	public Set<ToDos> getTodos() {
		return todos;
	}


	public void setTodos(Set<ToDos> todos) {
		this.todos = todos;
	}

}
