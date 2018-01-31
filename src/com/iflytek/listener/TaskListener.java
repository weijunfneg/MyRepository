package com.iflytek.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.iflytek.domain.People;
import com.iflytek.iterator.Iterator;
import com.iflytek.iterator.PeopleIdRepositoryImpl;

/**
 * Application Lifecycle Listener implementation class TaskListener
 *
 */
@WebListener("/*")
public class TaskListener implements ServletContextAttributeListener,HttpSessionAttributeListener{

	PeopleIdRepositoryImpl PeopleIdRepository = new PeopleIdRepositoryImpl();
	People sendPeople;

	@Override
	public void attributeAdded(ServletContextAttributeEvent scab) {
		String attributeName = scab.getName();
		for (Iterator iter = PeopleIdRepository.getIterator(); iter.hasNext();) {
			String obtainPeopleId = (String) iter.next();
			String messageAttributes = "message" + obtainPeopleId;
			if (messageAttributes.equals(attributeName)) {
				String message = (String) scab.getValue();
				People people = sendPeople;
				people.sendMessage(message, obtainPeopleId);
			}
		}
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent scab) {
		String attributeName = scab.getName();
		for (Iterator iter = PeopleIdRepository.getIterator(); iter.hasNext();) {
			String obtainPeopleId = (String) iter.next();
			String messageAttributes = "message" + obtainPeopleId;
			if (messageAttributes.equals(attributeName)) {
				String message = null;
				People people = sendPeople;
				people.sendMessage(message, obtainPeopleId);
			}
		}

	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent scab) {

	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent hsbe) {
		String hsbeName = hsbe.getName();
		if ("people".equals(hsbeName)) {
			sendPeople = (People) hsbe.getValue();
		}

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent hsbe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent hsbe) {
		// TODO Auto-generated method stub
		
	}

}
