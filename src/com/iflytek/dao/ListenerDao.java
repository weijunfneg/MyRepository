package com.iflytek.dao;

import java.util.List;

public interface ListenerDao {
	
	void updateMessageByPeopleId(String message, String peopleId) ;

	List<String> getPeopleIdList() ;

	String getPeopleIdentity(String peopleId) ;

}
