package com.iflytek.dao;

import java.util.List;

import com.iflytek.domain.People;
import com.iflytek.domain.Project;
import com.iflytek.domain.Task;

public interface PmanagerDao {

	List<Project> showProject();

	List<People> getPeopleInfo();

	void addProject(String projectName, String projectContent,String pmanagerId);

	void deleteProject(String projectId);

	void modifyProject(String projectId, String projectName, String projectContent);

	void addMember(People people);

	void modifyMember(People people);

	void deleteMember(String peopleId);

	People getPmemberInfoByPeopleId(String peopleId);

	int getCount();

	List<Project> findProductByPage(int index, int currentCount);

	int getMemberCount();

	List<People> findMemberByPage(int index, int currentCount);

	void addPhoto(String src, String peopleId);

	boolean projectIsExit(String projectName);

	boolean peopleIdIsExit(String peopleId);

	void deleteProjectDeleteTask(String projectId);

	void deleteMemberDeleteTask(String peopleId);

	void deleteMemberUpdateTask(String peopleId);

	Project getProjectInfoByProjectId(String projectId);

	Task getPmemberTaskByPeopleId(String peopleId);

	void addMemberTask(Task task, String peopleId);

	List<Task> showTask(String peopleId);

	Task getTaskBytaskId(String taskId);

	void modifyMemberTask(Task task, String taskId);

	void deleteMemberTask(String taskId);

	void updateTaskByTaskId(String taskId);

	String getPeopleIdByTaskId(String taskId) ;

	List<String> getAutoSearchWord(String search);

	People getMemberByName(String pname);

}
