package com.iflytek.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.iflytek.dao.DmanagerDao;
import com.iflytek.domain.People;
import com.iflytek.domain.Project;
import com.iflytek.utils.DataSourceUtils;

public class DmanagerDaoImpl implements DmanagerDao {

	@Override
	public int getCount()  {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from project";
		Long query=null;
		try {
			query = (Long) runner.query(sql, new ScalarHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return query.intValue();
	}

	@Override
	public List<Project> findProjectByPage(int index, int currentCount) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from project limit ?,?";
		String sql_02="select * from people where peopleId=?";
		List<Project> list=null;
		try {
			list = runner.query(sql,new BeanListHandler<Project>(Project.class),index,currentCount);
			for (int i = 0; i < list.size(); i++) {
				People people=runner.query(sql_02,new BeanHandler<People>(People.class),list.get(i).getPmanagerId());
				list.get(i).setPeople(people);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Project getProjectByProjectId(String projectId) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from project where projectId=?";
		Project project=null;
		try {
			project=runner.query(sql, new BeanHandler<Project>(Project.class),projectId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return project;
	}

}
