package com.iflytek.visit;

import java.util.List;

import com.iflytek.domain.PageBean;
import com.iflytek.domain.Project;
import com.iflytek.utils.BeanFactory;
import com.iflytek.web.service.PmanagerService;

public class ProjectList {
	
	private List<Project> projectList;
	private List<IProject> iProjectList;
	private PageBean projectPageList;
	private PmanagerService ps=(PmanagerService) BeanFactory.getBean("PmanagerService");
	private Integer currentPage;
	private Integer currentCount;
	
	public void initProject() {
		projectList=ps.showProject();
		
	}
	
	public PageBean returnProject() {
		 projectPageList=ps.findCategoryBycid(currentPage, currentCount);
		 return projectPageList;
	}
	
	public void addVister(ProjectView projectView) {
		for(IProject p:iProjectList) {
			p.accept(projectView);
		}
	}
	
	

	public ProjectList(Integer currentPage, Integer currentCount) {
		super();
		this.currentPage = currentPage;
		this.currentCount = currentCount;
	}

	public ProjectList() {
		super();
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}
	
	
	

}
