package com.iflytek.web.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iflytek.domain.PageBean;
import com.iflytek.domain.People;
import com.iflytek.utils.BeanFactory;
import com.iflytek.utils.LoggerUtils;
import com.iflytek.web.service.DmanagerService;

/**
 * Servlet implementation class DmanagerServlet
 */
@WebServlet("/dmanager")
public class DmanagerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	DmanagerService ds = (DmanagerService) BeanFactory.getBean("DmanagerSevice");

	// 查询项目进度项目功能
	public void showProgress(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		/*People people= (People) session.getAttribute("people");
		String peopleId=people.getPeopleId();
		List<Project> projectList= ds.showProgress(peopleId);
		request.setAttribute("projectList", projectList);*/
		
		People member=(People) session.getAttribute("people");
		
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null) {
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 5;
		PageBean pageBean = ds.showProject(currentPage, currentCount);
		request.setAttribute("pageBean", pageBean);
		
		String logInfo=member.getPeopleName()+"查看了项目进度";
		LoggerUtils.getInstance().writeLog(logInfo);
		request.getRequestDispatcher("/d_manager.jsp").forward(request, response);
	}
	
	public void remindPmanager(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String peopleId=request.getParameter("peopleId");
		String projectId=request.getParameter("projectId");
		ServletContext application=this.getServletContext();   
		String messageAttribute="message"+peopleId+"";
		String projectName=ds.getProjectNameByProjectId(projectId);
		String message="请抓经时间完成"+projectName;
		application.setAttribute(messageAttribute, message);
		showInfo(request, response);
	}

	public void showInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getAttribute("projectList") == null) {
			showProgress(request, response);
		}
	}
	
}
