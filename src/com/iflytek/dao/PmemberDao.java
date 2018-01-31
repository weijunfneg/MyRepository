package com.iflytek.dao;

import java.util.List;

import com.iflytek.domain.People;
import com.iflytek.domain.Task;

public interface PmemberDao {

	//List<Task> showEmpTask(String peopleId) ;

	void updateProject() ; 
	
	String deleteCharString(String sourceString, char chElemData);

	People getMemberInfo(String peopleId) ;

	//List<Task> showTask(String peopleId) ;

	void updateTask(List<Task> taskList) ;

	int getCount(String peopleId) ;

	List<Task> findPeopleTaskByPage(int index, int currentCount, String peopleId) ;


}
