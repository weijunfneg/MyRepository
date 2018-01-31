package com.iflytek.domain;

import java.util.List;

import com.iflytek.mediator.MessageMediator;

public class People {
	private String peopleId;
	private String peopleName;
	private String identity;
	private String password;
	private String photo;
	private String message;
	
	private List<Project> project;
	private List<Task> task;
	
	public void sendMessage(String message, String obtainPeopleId) {
		MessageMediator.showMessage(this, message, obtainPeopleId);
	}

	public People(String peopleName) {
		super();
		this.peopleName = peopleName;
	}


	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

	public List<Task> getTask() {
		return task;
	}

	public void setTask(List<Task> task) {
		this.task = task;
	}


	public People(String peopleId, String peopleName, String identity, String password, String photo, String message,
			List<Project> project, List<Task> task) {
		super();
		this.peopleId = peopleId;
		this.peopleName = peopleName;
		this.identity = identity;
		this.password = password;
		this.photo = photo;
		this.message = message;
		this.project = project;
		this.task = task;
	}

	public People() {
		
	}

}
