package com.example.ToDo.Model;

import java.time.LocalDateTime;

public class Tasks {
	
	private int id;
	private String title;
	private String description;
	private Status status;
	private Priority priority;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	public Tasks() {  //required for Json deserialization
		
	}
	
	public int getId() {
		return id;
	}
	
	
	public Tasks(int id, String title, String description, Status status, Priority priority) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.priority = priority;
	}


	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}


	


	

	

	public void setUpdatedAt(Object updatedAt2) {
		// TODO Auto-generated method stub
		
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	} 
	
	

}
