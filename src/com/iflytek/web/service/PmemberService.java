package com.iflytek.web.service;

import java.util.List;

import com.iflytek.domain.PageBean;
import com.iflytek.domain.People;
import com.iflytek.domain.Task;

public interface PmemberService {

	//List<Task> showEmpTask(String peopleId);

	void updateProject();

	People getMemberInfo(String peopleId);

	//List<Task> showTask(String peopleId);

	void updateTask(List<Task> taskList);

	PageBean showPeopleTask(int currentPage, int currentCount, String peopleId); 


}
