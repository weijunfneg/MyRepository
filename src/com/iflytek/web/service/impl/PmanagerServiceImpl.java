package com.iflytek.web.service.impl;

import java.util.List;

import com.iflytek.dao.PmanagerDao;
import com.iflytek.domain.PageBean;
import com.iflytek.domain.People;
import com.iflytek.domain.Project;
import com.iflytek.domain.Task;
import com.iflytek.utils.BeanFactory;
import com.iflytek.web.service.PmanagerService;

public class PmanagerServiceImpl implements PmanagerService {

	PmanagerDao pd = (PmanagerDao) BeanFactory.getBean("PmanagerDao");

	@Override
	public List<Project> showProject() {
		List<Project> projectList = pd.showProject();
		return projectList;
	}

	@Override
	public List<People> getPeopleInfo() {
		List<People> peopleList = pd.getPeopleInfo();
		return peopleList;
	}

	@Override
	public void addProject(String projectName, String projectContent,String pmanagerId) {
		pd.addProject(projectName, projectContent,pmanagerId);
	}

	@Override
	public void deleteProject(String projectId) {
		pd.deleteProjectDeleteTask(projectId);
		pd.deleteProject(projectId);
	}

	@Override
	public void modifyProject(String projectId, String projectName, String projectContent) {
		pd.modifyProject(projectId, projectName, projectContent);
	}

	@Override
	public void addMember(People people) {
		pd.addMember(people);
	}

	@Override
	public void modifyMember(People people) {
		pd.modifyMember(people);
	}

	@Override
	public void deleteMember(String peopleId) {
		pd.deleteMemberUpdateTask(peopleId);
		pd.deleteMemberDeleteTask(peopleId);
		pd.deleteMember(peopleId);
	}

	@Override
	public People getPmemberInfoByPeopleId(String peopleId) {
		People modifyEmp = pd.getPmemberInfoByPeopleId(peopleId);
		return modifyEmp;
	}

	@Override
	public PageBean findCategoryBycid(int currentPage, int currentCount) {
		PageBean<Project> pageBean = new PageBean<Project>();
		int totalCount = 0;
		totalCount = pd.getCount();
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);

		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		int index = (currentPage - 1) * currentCount;
		List<Project> list = pd.findProductByPage(index, currentCount);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public PageBean findMember(int currentPage, int currentCount) {
		PageBean<People> pageBean = new PageBean<People>();
		int totalCount = 0;
		totalCount = pd.getMemberCount();
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);

		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		int index = (currentPage - 1) * currentCount;
		List<People> list = pd.findMemberByPage(index, currentCount);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public void addPhoto(String src, String peopleId) {
		pd.addPhoto(src, peopleId);
	}

	@Override
	public boolean projectIsExit(String projectName) {
		boolean isExit = pd.projectIsExit(projectName);
		return isExit;
	}

	@Override
	public boolean peopleIdIsExit(String peopleId) {
		boolean isExit = pd.peopleIdIsExit(peopleId);
		return isExit;
	}

	@Override
	public String getPmemberNameByPeopleId(String peopleId) {
		People people = pd.getPmemberInfoByPeopleId(peopleId);
		return people.getPeopleName();
	}

	@Override
	public String getProjectName(String projectId) {
		Project project = pd.getProjectInfoByProjectId(projectId);
		return project.getProjectName();
	}

	@Override
	public Project getProjectInfoByProjectId(String projectId) {
		Project project = pd.getProjectInfoByProjectId(projectId);
		return project;
	}

	@Override
	public Task getPmemberTaskByPeopleId(String peopleId) {
		Task task = pd.getPmemberTaskByPeopleId(peopleId);
		return task;
	}

	@Override
	public void addMemberTask(Task task, String peopleId) {
		pd.addMemberTask(task, peopleId);
	}

	@Override
	public List<Task> showTask(String peopleId) {
		List<Task> task = pd.showTask(peopleId);
		return task;
	}

	@Override
	public Task modifyTask(String taskId) {
		Task task = pd.getTaskBytaskId(taskId);
		return task;
	}

	@Override
	public void modifyMemberTask(Task task, String taskId) {
		pd.modifyMemberTask(task, taskId);
	}

	@Override
	public void deleteMemberTask(String taskId) {
		pd.updateTaskByTaskId(taskId);
		pd.deleteMemberTask(taskId);
	}

	@Override
	public String getPeopleIdByTaskId(String taskId) {
		String peopleId = pd.getPeopleIdByTaskId(taskId);
		return peopleId;
	}

	@Override
	public List<String> getAutoSearchWord(String search) {
		return pd.getAutoSearchWord(search);
	}

	@Override
	public People getMemberByName(String pname) {
		return pd.getMemberByName(pname);
	}
}
