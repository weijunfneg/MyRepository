package com.iflytek.dao;

import java.util.List;

import com.iflytek.domain.Project;

public interface DmanagerDao {


	//public List<Project> showProgress(String peopleId) ;

	public int getCount() ;

	public List<Project> findProjectByPage(int index, int currentCount) ;

	public Project getProjectByProjectId(String projectId);
	
}
