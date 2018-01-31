package com.iflytek.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.iflytek.dao.LoginDao;
import com.iflytek.domain.People;
import com.iflytek.utils.DataSourceUtils;
import com.iflytek.utils.MD5Utils;

public class LoginDaoImpl implements LoginDao {

	@Override
	public People isExist(String peopleId, String password) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String Md5Password=MD5Utils.md5(password);
		String sql = "select * from people where peopleId='" + peopleId + "'and password='" + Md5Password + "'";
		People people=null;
		try {
			people = qr.query(sql, new BeanHandler<People>(People.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return people;
	}

	@Override
	public boolean isPeopleId(String peopleId) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from people where peopleId='" + peopleId + "'";
		People people=null;
		try {
			people = qr.query(sql, new BeanHandler<People>(People.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return people==null? false:true;
	}

	

}
