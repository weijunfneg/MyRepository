package com.iflytek.iterator;

import java.util.List;

import com.iflytek.utils.BeanFactory;
import com.iflytek.web.service.ListenerService;

public class PeopleIdRepositoryImpl implements Container {
	
	private ListenerService ls=(ListenerService) BeanFactory.getBean("ListenerService");
	public List<String> peopleIdList=ls.getPeopleIdList();
	
	@Override
	public Iterator getIterator() {
		return new PeopleIdIterator();
	}
	
	 private class PeopleIdIterator implements Iterator {

	      int index;

	      @Override
	      public boolean hasNext() {
	         if(index < peopleIdList.size()){
	            return true;
	         }
	         return false;
	      }

	      @Override
	      public Object next() {
	         if(this.hasNext()){
	            return peopleIdList.get(index++);
	         }
	         return null;
	      }        
	   }

}
