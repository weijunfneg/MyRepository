package com.iflytek.dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iflytek.dao.ListenerDao;
import com.iflytek.domain.People;
import com.iflytek.utils.DataSourceUtils;

public class ListenerDaoImpl implements ListenerDao {

	@Override
	public void updateMessageByPeopleId(String message, String peopleId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update  people set message=? where peopleId=?";
		try {
			qr.update(sql,message,peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<String> getPeopleIdList() {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select peopleId from people";
		List<People> peopleList=null;
		try {
			peopleList = qr.query(sql,new BeanListHandler<People>(People.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<String> peopleIdList=new ArrayList<>();
		for (People people : peopleList) {
			peopleIdList.add(people.getPeopleId());
		}
		return peopleIdList;
	}

	@Override
	public String getPeopleIdentity(String peopleId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select identity from people where peopleId=?";
		People people=null;
		try {
			people = qr.query(sql,new BeanHandler<People>(People.class),peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return people.getIdentity();
		
	}

}
