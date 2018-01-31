package com.iflytek.web.service;

import java.util.List;

import com.iflytek.domain.PageBean;
import com.iflytek.domain.People;
import com.iflytek.domain.Project;
import com.iflytek.domain.Task;

public interface PmanagerService {

	List<Project> showProject();

	List<People> getPeopleInfo();
	
	void addProject(String projectName, String projectContent, String pmanagerId);

	void deleteProject(String projectId);

	void modifyProject(String projectId, String projectName, String projectContent);

	void addMember(People people); 

	void modifyMember(People people);

	void deleteMember(String peopleId);
	
	People getPmemberInfoByPeopleId(String peopleId);

	PageBean findCategoryBycid(int currentPage, int currentCount);
	
	PageBean findMember(int currentPage, int currentCount); 
	
	void addPhoto(String src, String peopleId); 

	boolean projectIsExit(String projectName); 

	boolean peopleIdIsExit(String peopleId);

	String getPmemberNameByPeopleId(String peopleId);

	String getProjectName(String projectId);

	Project getProjectInfoByProjectId(String projectId);

	Task getPmemberTaskByPeopleId(String peopleId);

	void addMemberTask(Task task, String peopleId);

	List<Task> showTask(String peopleId);

	Task modifyTask(String taskId);

	void modifyMemberTask(Task task, String taskId);

	void deleteMemberTask(String taskId);

	String getPeopleIdByTaskId(String taskId);

	List<String> getAutoSearchWord(String search);

	People getMemberByName(String pname);
	
}
