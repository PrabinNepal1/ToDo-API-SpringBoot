package com.cognixia.jump.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyTemporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class ToDos  implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String description;
	
	@Column(columnDefinition = "boolean default false")
	private boolean completed;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@MapKeyTemporal(TemporalType.DATE)
	private LocalDate dueDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	public ToDos() {
		this(-1, "Please add your TODO", false, LocalDate.now());
	}

	public ToDos(Integer id, @NotBlank String description, boolean completed, LocalDate dueDate) {
		super();
		this.id = id;
		this.description = description;
		this.completed = completed;
		this.dueDate = dueDate;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ToDos [id=" + id + ", description=" + description + ", completed=" + completed + ", dueDate=" + dueDate
				+ ", user=" + user + "]";
	}

	

}
