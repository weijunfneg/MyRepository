package com.iflytek.web.service.impl;

import com.iflytek.dao.LoginDao;
import com.iflytek.domain.People;
import com.iflytek.utils.BeanFactory;
import com.iflytek.web.service.LoginSevice;

public class LoginServiceImpl implements LoginSevice {

	@Override
	public People isExist(String peopleId, String password) {
		LoginDao ld=(LoginDao) BeanFactory.getBean("LoginDao");
		People	people=ld.isExist(peopleId,password);
		return people;
	}

	@Override
	public boolean isPeopleId(String peopleId) {
		LoginDao ld=(LoginDao) BeanFactory.getBean("LoginDao");
		boolean	isExit=ld.isPeopleId(peopleId);
		return isExit;
	}

}
