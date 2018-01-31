package com.iflytek.web.service;

import java.util.List;

public interface ListenerService {
	
	void updateMessageByPeopleId(String message,String peopleId);

	List<String> getPeopleIdList();

	String getPeopleIdentity(String peopleId);


}
