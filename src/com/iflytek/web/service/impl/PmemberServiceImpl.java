package com.iflytek.web.service.impl;

import java.util.List;

import com.iflytek.dao.PmemberDao;
import com.iflytek.domain.PageBean;
import com.iflytek.domain.People;
import com.iflytek.domain.Task;
import com.iflytek.utils.BeanFactory;
import com.iflytek.web.service.PmemberService;

public class PmemberServiceImpl implements PmemberService {
	PmemberDao pd = (PmemberDao) BeanFactory.getBean("PmemberDao");

	/*@Override
	public List<Task> showEmpTask(String peopleId) {
		List<Task> empTaskList = pd.showEmpTask(peopleId);
		return empTaskList;
	}*/

	@Override
	public void updateProject() {
		pd.updateProject();
	}

	@Override
	public People getMemberInfo(String peopleId) {
		People people = pd.getMemberInfo(peopleId);
		return people;
	}

	/*@Override
	public List<Task> showTask(String peopleId) {
		List<Task> taskList = pd.showTask(peopleId);
		return taskList;
	}*/

	@Override
	public void updateTask(List<Task> taskList) {
		pd.updateTask(taskList);
	}

	@Override
	public PageBean showPeopleTask(int currentPage, int currentCount, String peopleId) {
		PageBean<Task> pageBean = new PageBean<Task>();
		int totalCount = 0;
		totalCount = pd.getCount(peopleId);
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);

		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		int index = (currentPage - 1) * currentCount;
		List<Task> list = pd.findPeopleTaskByPage(index, currentCount, peopleId);
		pageBean.setList(list);
		return pageBean;
	}

}
