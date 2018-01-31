package com.iflytek.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iflytek.domain.People;
import com.iflytek.utils.BeanFactory;
import com.iflytek.utils.LoggerUtils;
import com.iflytek.utils.VerifyCodeUtils;
import com.iflytek.web.service.LoginSevice;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginSevice ls=(LoginSevice) BeanFactory.getBean("LoginSevice");
		HttpSession session = request.getSession();
		String peopleId=request.getParameter("peopleId");
		String password=request.getParameter("password");
		People people=null;
		if(peopleId!=null||password!=null) {
			people=ls.isExist(peopleId,password);
		}
		
		if(people!=null) {
			String autoLogin = request.getParameter("autoLogin");
			if("autoLogin".equals(autoLogin)){
				Cookie cookie_peopleId = new Cookie("cookie_peopleId",peopleId);
				cookie_peopleId.setMaxAge(10*60);
				
				Cookie cookie_password = new Cookie("cookie_password",password);
				cookie_password.setMaxAge(10*60);
				
				response.addCookie(cookie_peopleId);
				response.addCookie(cookie_password);
			}
			String logInfo=(people.getPeopleName()+"登录了项目管理系统");
			LoggerUtils.getInstance().writeLog(logInfo);
			
			session.setAttribute("people", people);
			String identity=people.getIdentity();
			if("pmanager".equals(identity)) {
				//request.getRequestDispatcher("pmanager?method=showInfo").forward(request, response);
				response.sendRedirect(request.getContextPath()+"/pmanager");
			}else if("dmanager".equals(identity)) {
				//request.getRequestDispatcher("dmanager").forward(request, response);
				response.sendRedirect(request.getContextPath()+"/dmanager");
			}else if("pmember".equals(identity)) {
				//request.getRequestDispatcher("pmember").forward(request, response);
				response.sendRedirect(request.getContextPath()+"/pmember");
			}
			
		}else {
			if(peopleId==null||password==null) {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else {
				request.setAttribute("loginError", "用户名或密码错误");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}
		
	}
	
	
	// ajax异步校验数据库中是否存在该用户id
	public void isPeopleId(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String peopleId = request.getParameter("peopleId");
		LoginSevice ls=(LoginSevice) BeanFactory.getBean("LoginSevice");
		boolean isExist = ls.isPeopleId(peopleId);

		String json = "{\"isExist\":" + isExist + "}";
		response.getWriter().write(json);
	}
	
	public void sendVerifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
        //存入会话session  
        HttpSession session = request.getSession(true);  
        session.setAttribute("rand", verifyCode.toLowerCase());  
        //生成图片  
        int w = 70, h = 35;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
	
	// 校验登陆验证码是否正确
	public void verificationCode(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String code = request.getParameter("logincode");
		String code1 = (String) request.getSession().getAttribute("rand");
		boolean isExist = true;
		String code2 = code.toLowerCase();
		if (code != null && code1 != null) {
			if (code1.equals(code2)) {
				isExist = true;
			} else {
				isExist = false;
			}
		} else {
			isExist = false;
		}
		String json = "{\"isExist\":" + isExist + "}";
		response.getWriter().write(json);
	}

	// 退出
	public void quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("people");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	
}
