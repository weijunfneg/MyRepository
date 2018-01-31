package com.iflytek.dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.iflytek.dao.PmanagerDao;
import com.iflytek.domain.People;
import com.iflytek.domain.Project;
import com.iflytek.domain.Task;
import com.iflytek.utils.CommonsUtils;
import com.iflytek.utils.DataSourceUtils;
import com.iflytek.utils.MD5Utils;

public class PmanagerDaoImpl implements PmanagerDao {


	@Override
	public List<Project> showProject()  {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from project";
		List<Project> projectList=null;
		try {
			projectList = qr.query(sql,new BeanListHandler<Project>(Project.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projectList;
	}

	@Override
	public List<People> getPeopleInfo(){
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="select * from people where identity='pmember'";
		String sql_02="select * from project";
		String sql_03="select * from task";
		List<People> peopleList=null;
		List<Project> projectList=null;
		List<Task> taskList=null;
		try {
			peopleList = qr.query(sql_01,new BeanListHandler<People>(People.class));
			projectList = qr.query(sql_02,new BeanListHandler<Project>(Project.class));
			taskList = qr.query(sql_03,new BeanListHandler<Task>(Task.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < projectList.size(); i++) {
			for(int j = 0; j < taskList.size(); j++) {
				if(taskList.get(j).getProjectId().equals(projectList.get(i).getProjectId())) {
					taskList.get(j).setProject(projectList.get(i));
				}
				
			}
		}
		
		
		for(int i=0;i<peopleList.size();i++) {
			List<Task> task=new ArrayList<>();
			for (int j = 0; j < taskList.size(); j++) {
				if(peopleList.get(i).getPeopleId().equals(taskList.get(j).getPeopleId())) {
					task.add(taskList.get(j));
				}
			}
			peopleList.get(i).setTask(taskList);
		}
		return peopleList;
	}

	@Override
	public void addProject(String projectName, String projectContent,String pmanagerId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into project (projectId,projectName,projectContent,pmanagerId) value(?,?,?,?)";
		try {
			qr.update(sql,CommonsUtils.getUUID(),projectName,projectContent,pmanagerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteProject(String projectId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from project WHERE projectId = ?";
		try {
			qr.update(sql,projectId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void modifyProject(String projectId, String projectName, String projectContent) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update  project set projectName=?,projectContent=? where projectId=?";
		try {
			qr.update(sql,projectName,projectContent,projectId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addMember(People people) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="insert into people  value(?,?,?,?,?)";
		String peoplePassword =MD5Utils.md5(people.getPassword());
		Object []peopleInfo= {people.getPeopleId(),people.getPeopleName(),"pmember",peoplePassword,people.getPhoto()};
		try {
			qr.update(sql_01,peopleInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifyMember(People people) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql_01="update people set password=? where peopleId=?";
		String sql_02="update people set photo='"+people.getPhoto()+"',password=? where peopleId=?";
		String peoplePassword =MD5Utils.md5(people.getPassword());
		Object []peopleInfo= {peoplePassword,people.getPeopleId()};
		try {
			if(people.getPhoto()==null) {
				qr.update(sql_01,peopleInfo);
			}else {
				qr.update(sql_02,peopleInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMember(String peopleId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="delete from people where peopleid=?";
		try {
			qr.update(sql_01,peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public People getPmemberInfoByPeopleId(String peopleId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from people where peopleId=?";
		People modifyEmp=null;
		try {
			modifyEmp = qr.query(sql, new BeanHandler<People>(People.class),peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modifyEmp;
	}

	@Override
	public void addPhoto(String src, String peopleId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="update people set photo=? where peopleId=?";
		try {
			qr.update(sql_01,src,peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean projectIsExit(String projectName) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="select * from project where projectName='"+projectName+"'";
		Project project=null;
		try {
			project = qr.query(sql_01,new BeanHandler<Project>(Project.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(project!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean peopleIdIsExit(String peopleId){
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="select * from people where peopleId='"+peopleId+"'";
		People people=null;
		try {
			people = qr.query(sql_01,new BeanHandler<People>(People.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(people!=null) {
			return true;
		}
		return false;
	}

	@Override
	public int getCount() {
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
	public List<Project> findProductByPage(int index, int currentCount) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from project limit ?,?";
		List<Project> list=null;
		try {
			list = runner.query(sql,new BeanListHandler<Project>(Project.class),index,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getMemberCount() {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from people where identity='pmember'";
		Long query=null;
		try {
			query = (Long) runner.query(sql, new ScalarHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return query.intValue();
	}

	@Override
	public List<People> findMemberByPage(int index, int currentCount) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="select * from people where identity='pmember' limit ?,?";
		String sql_02="select * from project";
		String sql_03="select * from task";
		List<People> peopleList=null;
		List<Project> projectList=null;
		List<Task> taskList=null;
		try {
			peopleList = qr.query(sql_01,new BeanListHandler<People>(People.class),index,currentCount);
			projectList = qr.query(sql_02,new BeanListHandler<Project>(Project.class));
			taskList = qr.query(sql_03,new BeanListHandler<Task>(Task.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < projectList.size(); i++) {
			for (int j = 0; j < taskList.size(); j++) {
				if (taskList.get(j).getProjectId().equals(projectList.get(i).getProjectId())) {
					taskList.get(j).setProject(projectList.get(i));
				}

			}
		}

		for (int i = 0; i < peopleList.size(); i++) {
			List<Task> task = new ArrayList<>();
			for (int j = 0; j < taskList.size(); j++) {
				if (peopleList.get(i).getPeopleId().equals(taskList.get(j).getPeopleId())) {
					task.add(taskList.get(j));
				}
			}
			peopleList.get(i).setTask(taskList);
		}

		return peopleList;
	}

	@Override
	public void deleteProjectDeleteTask(String projectId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="delete from task  where projectId=?";
		try {
			qr.update(sql_01,projectId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteMemberDeleteTask(String peopleId){
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_03="delete from task where peopleId=?";
		try {
			qr.update(sql_03,peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMemberUpdateTask(String peopleId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="update task set peopleId=?  where peopleId=?";
		try {
			qr.update(sql_01,null,peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Project getProjectInfoByProjectId(String projectId){
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from project where projectId=?";
		Project project=null;
		try {
			project = runner.query(sql,new BeanHandler<Project>(Project.class),projectId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return project;
	}

	@Override
	public Task getPmemberTaskByPeopleId(String peopleId) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from task where peopleId=?";
		Task task=null;
		try {
			task = runner.query(sql,new BeanHandler<Task>(Task.class),peopleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return task;
	}

	@Override
	public void addMemberTask(Task task, String peopleId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into task (taskId,taskName,taskProgress,peopleId,projectId) value(?,?,?,?,?)";
		String taskId=CommonsUtils.getUUID();
		Object []taskInfo= {taskId,task.getTaskName(),task.getTaskProgress(),peopleId,task.getProjectId()};
		try {
			qr.update(sql,taskInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Task> showTask(String peopleId) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from task where peopleId=?";
		String sql_02="select *from project where projectId=?";
		List<Task> task=null;
		try {
			task = runner.query(sql,new BeanListHandler<Task>(Task.class),peopleId);
			for (int i = 0; i < task.size(); i++) {
				Project project= runner.query(sql_02,new BeanHandler<Project>(Project.class),task.get(i).getProjectId());
				task.get(i).setProject(project);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return task;
	}

	@Override
	public Task getTaskBytaskId(String taskId) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from task where taskId=?";
		Task task=null;
		try {
			task = runner.query(sql,new BeanHandler<Task>(Task.class),taskId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return task;
	}

	@Override
	public void modifyMemberTask(Task task,String taskId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="update task set taskName=?,projectId=?  where taskId=?";
		try {
			qr.update(sql_01,task.getTaskName(),task.getProjectId(),taskId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMemberTask(String taskId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="delete from task  where taskId=?";
		try {
			qr.update(sql_01,taskId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTaskByTaskId(String taskId) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="update task set peopleId=?,projectId=?  where taskId=?";
		try {
			qr.update(sql_01,null,null,taskId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getPeopleIdByTaskId(String taskId) {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from task where taskId=?";
		Task task=null;
		try {
			task = runner.query(sql,new BeanHandler<Task>(Task.class),taskId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return task.getPeopleId();
	}

	@Override
	public List<String> getAutoSearchWord(String search) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="select * from people where peopleName like '%"+search+"%'";
		List<String> peopleName=new ArrayList<String>();
		List<People> peopleList=null;
		try {
			peopleList=qr.query(sql_01, new BeanListHandler<People>(People.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (People people : peopleList) {
			peopleName.add(people.getPeopleName());
		}
		return peopleName;
	}

	@Override
	public People getMemberByName(String pname) {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql_01="select * from people where peopleName like '"+pname+"'";
		People people=null;
		try {
			people= qr.query(sql_01, new BeanHandler<People>(People.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return people;
	}

}
