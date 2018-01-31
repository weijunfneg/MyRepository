package com.iflytek.web.service.impl;

import java.util.List;

import com.iflytek.dao.ListenerDao;
import com.iflytek.utils.BeanFactory;

public class ListenerServiceImpl implements com.iflytek.web.service.ListenerService {
	
	private ListenerDao ld=(ListenerDao) BeanFactory.getBean("ListenerDao");
	
	@Override
	public void updateMessageByPeopleId(String message, String peopleId) {
			ld.updateMessageByPeopleId(message, peopleId);
	}

	@Override
	public List<String> getPeopleIdList() {
		List<String>	peopleIdList = ld.getPeopleIdList();
		return peopleIdList;
	}

	@Override
	public String getPeopleIdentity(String peopleId) {
		String	identity=ld.getPeopleIdentity(peopleId);
		return identity;
	}

}
