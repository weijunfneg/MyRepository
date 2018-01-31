package com.iflytek.web.service.impl;

import java.util.List;

import com.iflytek.dao.DmanagerDao;
import com.iflytek.domain.PageBean;
import com.iflytek.domain.Project;
import com.iflytek.utils.BeanFactory;
import com.iflytek.web.service.DmanagerService;

public class DmanagerServiceImpl implements DmanagerService {
	DmanagerDao dd=(DmanagerDao) BeanFactory.getBean("DmanagerDao");

	/*@Override
	public List<Project> showProgress(String peopleId) {
		List<Project>	projectList=dd.showProgress(peopleId);
		return projectList;
	}*/

	@Override
	public PageBean showProject(int currentPage, int currentCount) {
		PageBean <Project> pageBean =new PageBean<Project>();
		int totalCount=0;
			totalCount=dd.getCount();
		int totalPage=(int)Math.ceil(1.0*totalCount/currentCount);
		
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		int index=(currentPage-1)*currentCount;
		List<Project>	list =dd.findProjectByPage(index,currentCount);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public String getProjectNameByProjectId(String projectId) {
		Project project=dd.getProjectByProjectId(projectId);
		return project.getProjectName();
	}

}
