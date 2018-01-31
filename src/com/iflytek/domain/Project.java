package com.iflytek.domain;

public class Project {
	private String projectId;
	private String projectName;
	private String projectContent;
	private double totalProgress;
	private String pmanagerId;
	
	private Task task;
	private People people;
	
	/*private String taskName;
	private String empProgress;
	private String empName;
	private String empId;
	private String pmanagerName;*/
	
	public Project(){
		
	}
	

	public Project(String projectName) {
		super();
		this.projectName = projectName;
	}


	public Project(String projectId, String projectName, String projectContent, double totalProgress, String pmanagerId,
			Task task, People people) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectContent = projectContent;
		this.totalProgress = totalProgress;
		this.pmanagerId = pmanagerId;
		this.task = task;
		this.people = people;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getProjectContent() {
		return projectContent;
	}


	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}


	public double getTotalProgress() {
		return totalProgress;
	}


	public void setTotalProgress(double totalProgress) {
		this.totalProgress = totalProgress;
	}


	public String getPmanagerId() {
		return pmanagerId;
	}


	public void setPmanagerId(String pmanagerId) {
		this.pmanagerId = pmanagerId;
	}


	public Task getTask() {
		return task;
	}


	public void setTask(Task task) {
		this.task = task;
	}


	public People getPeople() {
		return people;
	}


	public void setPeople(People people) {
		this.people = people;
	}

	
}
