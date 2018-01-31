<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.net.*"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <div>
    <div align="center">
		<div>
		    <h3>欢迎使用项目进度管理系统</h3>
			<span class="glyphicon glyphicon-user"></span>欢迎您:${people.peopleName }
			<a class="btn btn-success btn-sm" href="${pageContext.request.contextPath}/login?method=quit" 
			role="button"><span class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;退出</a>
		</div>
		<div>
			<img alt="头像" src="${pageContext.request.contextPath }/${people.photo}"
			 class="img-circle" style=" width:80px; height:80px">
		</div>
	</div>
	<hr>
	</div>