package com.iflytek.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.iflytek.domain.PageBean;
import com.iflytek.domain.People;
import com.iflytek.domain.Task;
import com.iflytek.utils.BeanFactory;
import com.iflytek.utils.LoggerUtils;
import com.iflytek.utils.TransactionHandler;
import com.iflytek.web.service.PmemberService;

/**
 * Servlet implementation class PmemberServlet
 */
@WebServlet("/pmember")
public class PmemberServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	PmemberService ps = (PmemberService) BeanFactory.getBean("PmemberService");
	private TransactionHandler handler=(TransactionHandler) BeanFactory.getBean("TransactionHandler");
	private PmemberService proxy;
	
	// 查询任务功能
	public void showTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmemberService) handler.getProxy();
		HttpSession session = request.getSession();
		People people= (People) session.getAttribute("people");
		String peopleId=people.getPeopleId();
		
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null) {
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 5;
		PageBean pageBean = ps.showPeopleTask(currentPage, currentCount,peopleId);
		request.setAttribute("pageBean", pageBean);
		
		request.getRequestDispatcher("/p_member.jsp").forward(request, response);
	}

	// 删除信息
	public void removeMessage(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext application = this.getServletContext();
		People people = (People) session.getAttribute("people");
		people.setMessage(null);
		String peopleId = people.getPeopleId();
		String messageAttributes = "message" + peopleId + "";
		if (application.getAttribute(messageAttributes) == null) {
			application.setAttribute(messageAttributes, peopleId);
		}
		application.removeAttribute(messageAttributes);
		showInfo(request, response);
	}
	
	// 更新任务进度功能
	public void updateTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		handler.setTarget(ps);
		proxy=(PmemberService) handler.getProxy();
		HttpSession session = request.getSession();
		List<Task> taskList=new ArrayList<>();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> parseRequest = upload.parseRequest(request);
			for(FileItem item : parseRequest){
				boolean formField = item.isFormField();
				if(formField){
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					Task task=new Task(fieldValue,fieldName);
					taskList.add(task);
				}
			}
			ps.updateTask(taskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ps.updateProject();
		People member=(People) session.getAttribute("people");
		String logInfo=(member.getPeopleName()+"更新了任务进度");
		LoggerUtils.getInstance().writeLog(logInfo);
		showInfo(request,response);
	}

	// 
	public void showInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getAttribute("empTaskList") == null) {
			showTask(request, response);
		}
	}

}
