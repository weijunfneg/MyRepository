package com.iflytek.web.service;

import com.iflytek.domain.People;

public interface LoginSevice {

	People isExist(String peopleId, String password);

	boolean isPeopleId(String peopleId);

}
