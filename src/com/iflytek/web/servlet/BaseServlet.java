package com.iflytek.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/baseServlet")
@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String methodName = request.getParameter("method");
			if(methodName==null) {
				methodName="showInfo";
			}
			
			Class clazz = this.getClass();
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}