package com.iflytek.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter("*.jsp")
public class AutoLoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String cookie_peopleId = null;
		String cookie_password = null;
		Cookie[] cookies = req.getCookies();
		if(cookies!=null){
			for(Cookie cookie : cookies){
				if("cookie_peopleId".equals(cookie.getName())){
					cookie_peopleId = cookie.getValue();
				}
				if("cookie_password".equals(cookie.getName())){
					cookie_password = cookie.getValue();
				}
			}
		}
		if(cookie_peopleId==null&&cookie_password==null){
			
			if(session.getAttribute("people")==null) {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
			
		}
		
		chain.doFilter(req, resp);
		
	}
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void destroy() {
		
	}

}
