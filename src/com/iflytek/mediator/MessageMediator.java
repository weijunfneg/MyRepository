package com.iflytek.mediator;

import java.util.Date;

import com.iflytek.domain.People;
import com.iflytek.utils.BeanFactory;
import com.iflytek.web.service.ListenerService;

public class MessageMediator {
	private static ListenerService ls=(ListenerService) BeanFactory.getBean("ListenerService");
	public static void showMessage(People people, String message, String obtainPeopleId){
		String messages=null;
		if(message!=null) {
			messages=(new Date().toString()+ " [" + people.getPeopleName() +"] : " + message);
		}
	      ls.updateMessageByPeopleId(messages, obtainPeopleId);
	   }
	
}
