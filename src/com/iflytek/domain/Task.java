package com.iflytek.domain;

public class Task {
	private String taskId;
	private String taskName;
	private String taskProgress;
	private String peopleId;
	private String projectId;
	
	private People people;
	private Project project;
	
	/*private String projectName;
	private String projectContent;
	private String empName;*/
	public Task() {
		
	}
	
	
	
	public Task(String taskProgress, String taskId) {
		super();
		this.taskProgress = taskProgress;
		this.taskId = taskId;
	}

	public Task(String taskId, String taskName, String taskProgress, String peopleId, String projectId, People people,
			Project project) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskProgress = taskProgress;
		this.peopleId = peopleId;
		this.projectId = projectId;
		this.people = people;
		this.project = project;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskProgress() {
		return taskProgress;
	}

	public void setTaskProgress(String taskProgress) {
		this.taskProgress = taskProgress;
	}

	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}
