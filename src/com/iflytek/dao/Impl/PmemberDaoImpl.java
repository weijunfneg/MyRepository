package com.iflytek.dao.Impl;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.iflytek.dao.PmemberDao;
import com.iflytek.domain.People;
import com.iflytek.domain.Project;
import com.iflytek.domain.Task;
import com.iflytek.utils.DataSourceUtils;

public class PmemberDaoImpl implements PmemberDao {

	@Override
	public void updateProject() {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql_1="select *from project";
		String sql_2="select *from task where projectId=?";
		String sql_3 ="update project set totalprogress=? where projectId=?";
		try {
			List<Project> projectList=qr.query(sql_1, new BeanListHandler<Project>(Project.class));
			for (int i = 0; i < projectList.size(); i++) {
				List<Task> taskList=qr.query(sql_2, new BeanListHandler<Task>(Task.class), projectList.get(i).getProjectId());
				String EmpProgress = null;
				double totalProgress = 0;
				for (Task task : taskList) {
					EmpProgress = task.getTaskProgress();
					if (EmpProgress == null) {
						EmpProgress = "0";
					}
					EmpProgress = deleteCharString(EmpProgress, '%');
					totalProgress += Double.valueOf(EmpProgress);
					DecimalFormat df = new DecimalFormat("######0.0");
					df.format(totalProgress);
					qr.update(sql_3, totalProgress, projectList.get(i).getProjectId());
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String deleteCharString(String sourceString, char chElemData) {
		String deleteString = "";
        for (int i = 0; i < sourceString.length(); i++) {
            if (sourceString.charAt(i) != chElemData) {
                deleteString += sourceString.charAt(i);
            }
        }
        return deleteString;
	}

	@Override
	public People getMemberInfo(String peopleId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from people where peopleId=?";
		try {
			return qr.query(sql, new BeanHandler<People>(People.class),peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateTask(List<Task> taskList) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());	
		String sql="update task set taskProgress=? where taskId=?";
		for (int i = 0; i < taskList.size(); i++) {
			if(!taskList.get(i).getTaskProgress().equals("提交")) {
				String taskProgress=taskList.get(i).getTaskProgress();
				String taskId=taskList.get(i).getTaskId();
				try {
					runner.update(sql,taskProgress,taskId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}


	@Override
	public int getCount(String peopleId) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from task where peopleId=?";
		Long query=null;
		try {
			query = (Long) runner.query(sql, new ScalarHandler(),peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return query.intValue();
	}


	@Override
	public List<Task> findPeopleTaskByPage(int index, int currentCount, String peopleId) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from task where peopleId=? limit ?,? ";
		String sql_02="select *from project where projectId=?";
		List<Task> list=null;
		try {
			list = runner.query(sql,new BeanListHandler<Task>(Task.class),peopleId,index,currentCount);
			for (int i = 0; i < list.size(); i++) {
				Project project= runner.query(sql_02,new BeanHandler<Project>(Project.class),list.get(i).getProjectId());
				list.get(i).setProject(project);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
