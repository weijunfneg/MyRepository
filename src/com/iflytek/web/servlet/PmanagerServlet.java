package com.iflytek.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.iflytek.domain.PageBean;
import com.iflytek.domain.People;
import com.iflytek.domain.Project;
import com.iflytek.domain.Task;
import com.iflytek.utils.BeanFactory;
import com.iflytek.utils.LoggerUtils;
import com.iflytek.utils.TransactionHandler;
import com.iflytek.web.service.PmanagerService;

@WebServlet("/pmanager")
public class PmanagerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private PmanagerService ps=(PmanagerService) BeanFactory.getBean("PmanagerService");
	private TransactionHandler handler=(TransactionHandler) BeanFactory.getBean("TransactionHandler");
	private PmanagerService proxy;
	//显示所有信息
	public void showInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getAttribute("projectList")==null && request.getAttribute("peopleList")==null) {
			showProject(request,response);
			selectMember(request,response);
			request.getRequestDispatcher("/p_manager.jsp").forward(request, response);
		}
	}
	
	//显示项目列表
	public void showProject(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null) {
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 5;
		PageBean pageBean = ps.findCategoryBycid(currentPage, currentCount);
		request.setAttribute("pageBean", pageBean);
	}
	
	//显示员工所有信息
	public void selectMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String peopleId = request.getParameter("peopleId");
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null) {
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 5;
		PageBean pageBean = ps.findMember(currentPage, currentCount);
		request.setAttribute("peoplePageBean", pageBean);
		request.setAttribute("peopleId", peopleId);
	}
	
	//添加项目功能
	public void addProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		HttpSession session = request.getSession();
		String projectName=request.getParameter("projectName");
		String projectContent=request.getParameter("projectContent");
		People people=(People) session.getAttribute("people");
		ps.addProject(projectName,projectContent,people.getPeopleId());
		session.removeAttribute("projectList");
		String logInfo=(people.getPeopleName()+"添加了一个名为："+projectName+"的项目 ");
		LoggerUtils.getInstance().writeLog(logInfo);
		showInfo(request,response);
	}

	// 修改项目功能
	public void getModifyProject(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String projectId = request.getParameter("projectId");
		Project project =ps.getProjectInfoByProjectId(projectId);
		request.setAttribute("project", project);
		request.getRequestDispatcher("modifyProject.jsp").forward(request, response);
	}
	
	
	//修改项目功能
	public void modifyProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String projectId=request.getParameter("projectId");
		String projectName=request.getParameter("projectName");
		String projectContent=request.getParameter("projectContent");
		ps.modifyProject(projectId,projectName,projectContent);
		HttpSession session = request.getSession();
		session.removeAttribute("projectList");
		People people=(People) session.getAttribute("people");
		String logInfo=people.getPeopleName()+"将项目该为："+projectName;
		LoggerUtils.getInstance().writeLog(logInfo);
		showInfo(request,response);
	}

	//删除项目功能
	public void deleteProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String projectId=request.getParameter("projectId");
		String projectName=ps.getProjectName(projectId);
		ps.deleteProject(projectId);
		HttpSession session = request.getSession();
		session.removeAttribute("projectList");
		People people=(People) session.getAttribute("people");
		String logInfo=people.getPeopleName()+"删除了'"+projectName+"'项目";
		LoggerUtils.getInstance().writeLog(logInfo);
		showInfo(request,response);
	}

	//删除员工功能
	public void deleteMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String peopleId=request.getParameter("peopleId");
		String peopleName=ps.getPmemberNameByPeopleId(peopleId);
		ps.deleteMember(peopleId);
		HttpSession session = request.getSession();
		People people=(People) session.getAttribute("people");
		String logInfo=people.getPeopleName()+"删除了"+peopleName;
		LoggerUtils.getInstance().writeLog(logInfo);
		session.removeAttribute("peopleList");
		showInfo(request,response);
	}
	
	//准备添加员工
	public void addMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Project> projectList = ps.showProject();
		request.setAttribute("projectList", projectList);
		request.getRequestDispatcher("/addMember.jsp").forward(request, response);
	}
	
	//获得要修改员工信息功能
	public void getModifyMember(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String peopleId=request.getParameter("peopleId");
		List<Project> projectList = ps.showProject();
		request.setAttribute("projectList", projectList);
		People modifyEmp=ps.getPmemberInfoByPeopleId(peopleId);
		request.setAttribute("modifyEmp", modifyEmp);
		Task task=ps.getPmemberTaskByPeopleId(peopleId);
		request.setAttribute("task", task);
		request.getRequestDispatcher("/modifyMember.jsp").forward(request, response);
	}
	
	// 展示员工任务
	public void showTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String peopleId = request.getParameter("peopleId");
		People modifyEmp = ps.getPmemberInfoByPeopleId(peopleId);
		request.setAttribute("modifyEmp", modifyEmp);
		
		List<Task> taskList = ps.showTask(peopleId);
		request.setAttribute("taskList", taskList);
		
		request.getRequestDispatcher("/showTask.jsp").forward(request, response);
	}
	
	// 准备添加员工任务
	public void getAddMemberTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String peopleId=request.getParameter("peopleId");
		List<Project> projectList = ps.showProject();
		request.setAttribute("projectList", projectList);
		People modifyEmp=ps.getPmemberInfoByPeopleId(peopleId);
		request.setAttribute("modifyEmp", modifyEmp);
		request.getRequestDispatcher("/addTask.jsp").forward(request, response);
	}
	
	
	// 添加员工任务
	public void addMemberTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		Task task = new Task();
		try {
			BeanUtils.populate(task, request.getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		String peopleId=request.getParameter("peopleId");
		ps.addMemberTask(task,peopleId);
		ServletContext application=this.getServletContext();   
		String messageAttribute="message"+peopleId+"";
		String taskName=request.getParameter("taskName");
		String message="您新添加了"+taskName+",等任务";
		application.setAttribute(messageAttribute, message);
		showInfo(request,response);
	}

	// 删除信息
	public void removeMessage(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();
		ServletContext application=this.getServletContext();
		People people=(People)session.getAttribute("people");
		people.setMessage(null);
		String peopleId=people.getPeopleId();
		String messageAttributes="message"+peopleId+"";
		
		if(application.getAttribute(messageAttributes)==null){
			application.setAttribute(messageAttributes, peopleId);
		}
		application.removeAttribute(messageAttributes); 
		showInfo(request, response);
	}
	
	
	// 获取修改员工任务信息
	public void getModifyMemberTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String peopleId = request.getParameter("peopleId");
		String taskId = request.getParameter("taskId");
		Task modifyTask=ps.modifyTask(taskId);
		List<Project> projectList = ps.showProject();
		request.setAttribute("projectList", projectList);
		People modifyEmp = ps.getPmemberInfoByPeopleId(peopleId);
		request.setAttribute("modifyEmp", modifyEmp);
		List<Task> taskList = ps.showTask(peopleId);
		request.setAttribute("taskList", taskList);
		request.setAttribute("modifyTask", modifyTask);
		request.getRequestDispatcher("/modifyTask.jsp").forward(request, response);
	}
	
	// 修改员工任务功能
	public void modifyMemberTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		Task task = new Task();
		try {
			BeanUtils.populate(task, request.getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		String taskId=request.getParameter("taskId");
		ps.modifyMemberTask(task,taskId);
		
		String peopleId=ps.getPeopleIdByTaskId(taskId);
		ServletContext application=this.getServletContext();   
		String updateTaskPeopleId="updateTask"+peopleId+"";
		application.setAttribute(updateTaskPeopleId, peopleId);
		
		showInfo(request,response);
	}

	// 删除员工任务功能
	public void deleteMemberTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String taskId=request.getParameter("taskId");
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		ps.deleteMemberTask(taskId);
		showInfo(request, response);
	}

	
	//添加项目时判断该项目名是否已存在
	public void projectIsExit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String projectName=request.getParameter("projectName");
		boolean isExist=ps.projectIsExit(projectName);
		String json = "{\"isExist\":"+isExist+"}";
		response.getWriter().write(json);
	}
	
	//判断员工id是否已经存在
	public void peopleIdIsExit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String peopleId=request.getParameter("peopleId");
		boolean isExist=ps.peopleIdIsExit(peopleId);
		String json = "{\"isExist\":"+isExist+"}";
		response.getWriter().write(json);
	}
	
	//自动补全
	public void autoSearchWord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String search=request.getParameter("search");
		List<String> searchList=ps.getAutoSearchWord(search);
		Gson gson = new Gson();
		String json = gson.toJson(searchList);
		response.getWriter().write(json);
	}
	
	//搜索
	public void searchMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		String pname=request.getParameter("pname");
		People people =ps.getMemberByName(pname);
		System.out.println("mpeople"+ people.getPeopleId());
		request.setAttribute("mpeople", people);
		Gson gson = new Gson();
		String json = gson.toJson(people);
		response.getWriter().write(json);
	}
	
	//图标
	public void searchChart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmanagerService) handler.getProxy();
		List<Project> projectList =ps.showProject();
		List<Object> projectName=new ArrayList<>();
		List<Object> totalProgress=new ArrayList<>();
		for (Project pr : projectList) {
			projectName.add(pr.getProjectName());
			totalProgress.add(pr.getTotalProgress());
		}
		
		Map<String,List<Object>> project=new HashMap<>();
		project.put("projectName", projectName);
		project.put("totalProgress", totalProgress);
		Gson gson = new Gson();
		String json = gson.toJson(project);
		response.getWriter().write(json);
	}

}
