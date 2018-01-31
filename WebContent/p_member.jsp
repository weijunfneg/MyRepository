<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.iflytek.domain.People"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="js/jquery.qrcode.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<!-- 引入表单校验jquery插件 -->
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />


<script type="text/javascript">
$(function(){
	$("#myAlert").bind('closed.bs.alert', function () {
		 document.location.href="${pageContext.request.contextPath }/pmember?method=removeMessage";
    });
});
</script> 

<title>项目成员</title>
</head>
<body background="img/background.jpg">
	<jsp:include page="/header.jsp"></jsp:include>
	<c:if test="${!empty people.message}">
		<div id="myAlert" class="alert alert-warning">
			<a href="#" class="close" data-dismiss="alert">&times;</a> 
			<strong>${people.message}</strong>。
		</div>
	</c:if>
	<div class="container"style="width: 80%;">
	<form action="${pageContext.request.contextPath }/pmember?method=updateTask"
		  enctype="multipart/form-data" method="post">
		<table border="1" class="table table-hover">
			<tr>
				<td>所属项目</td>
				<td>项目内容</td>
				<td>我的任务</td>
				<td>填写任务进度</td>
			</tr>
			<c:if test="${empty pageBean.list}">
				<td>暂无项目</td>
				<td>暂无任务</td>
				<td><input type="text" name="taskProgress"  value="暂无进度"/></td>
			</c:if>
			<c:forEach var="taskList" items="${pageBean.list }">
				<tr>
					<td>${taskList.project.projectName}</td>
					<td>${taskList.project.projectContent}</td>
					<td>${taskList.taskName}</td> 
					<td>
						<input type="text" name="${taskList.taskId}"  value="${taskList.taskProgress}"/>
					</td>
				</tr>
			</c:forEach>
			<tr><td><input type="submit" name="quit"  value="提交"/></td></tr>
		</table>
		
		<!--分页 -->
		<div style="width: 380px; margin: 0 auto; margin-top: 20px; text-align:center;">
		<ul class="pagination" style="text-align: center; margin-top: 0px;">

		<!-- 上一页 -->
			<c:if test="${pageBean.currentPage==1 }">
				<li class="disabled"><a href="javascript:void(0);"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>
			<c:if test="${pageBean.currentPage!=1 }">
				<li><a
						href="${pageContext.request.contextPath}/pmember?method=showInfo&currentPage=${pageBean.currentPage-1 }"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>
		<!-- 显示每一页 -->
			<c:forEach begin="1" end="${pageBean.totalPage }" var="page">
				<!-- 判断是否是当前页 -->
				<c:if test="${page==pageBean.currentPage }">
					<li class="active"><a href="javascript:void(0);">${page }</a></li>
				</c:if>
				<c:if test="${page!=pageBean.currentPage }">
					<li><a
						href="${pageContext.request.contextPath}/pmember?method=showInfo&currentPage=${page }">${page }</a></li>
				</c:if>
			</c:forEach>
		<!-- 下一页 -->
			<c:if test="${pageBean.currentPage==pageBean.totalPage }">
				<li class="disabled"><a href="javascript:void(0);"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
			<c:if test="${pageBean.currentPage!=pageBean.totalPage }">
				<li><a
					href="${pageContext.request.contextPath}/pmember?method=showInfo&currentPage=${pageBean.currentPage+1 }"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
			</ul>
			</div>
		<!-- 分页结束 -->
		
	</form>
	</div>
</body>
</html>