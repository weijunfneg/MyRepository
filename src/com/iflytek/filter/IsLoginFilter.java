package com.iflytek.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebFilter("*.jsp")
public class IsLoginFilter implements Filter {

    public IsLoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		if(session.getAttribute("people")==null) {
			//request.getRequestDispatcher("/login.jsp").forward(request, response);
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
		}
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
