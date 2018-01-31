package com.iflytek.web.service;


import com.iflytek.domain.PageBean;

public interface DmanagerService {


	//List<Project> showProgress(String peopleId);

	PageBean showProject(int currentPage, int currentCount);

	String getProjectNameByProjectId(String projectId);
}
