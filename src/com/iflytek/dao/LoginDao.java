package com.iflytek.dao;


import com.iflytek.domain.People;

public interface LoginDao {

	People isExist(String peopleId, String password) ;

	boolean isPeopleId(String peopleId) ;

}
