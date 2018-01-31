<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<title>部门经理</title>
</head>
<body background="img/background.jpg">
	<jsp:include page="/header.jsp"></jsp:include>
	<div class="container"style="width: 80%;">
	<table border="1" class="table table-hover">
		<tr>
			<!-- <td>项目经理</td> -->
			<td>项目名称</td>
			<td>项目内容</td>
			<td>项目总进度</td>
			<td>项目负责人</td>
			<td>功能1</td>
		</tr>
		<c:forEach var="projectList" items="${pageBean.list }">
			<tr>
				<td>${projectList.projectName}</td>
				<td>${projectList.projectContent}</td>
				<td>
					<div class="progress progress-striped active">
						<div class="progress-bar progress-bar-success" role="progressbar"
							aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
							style="width: ${projectList.totalProgress}%;">
							<span  style="color:blue;">${projectList.totalProgress}% 完成</span>
						</div>
					</div>
				</td>
				<td>${projectList.people.peopleName}</td>
				<td><a class="btn btn-warning btn-sm" role="button"
					href="${pageContext.request.contextPath }/dmanager?method=remindPmanager&
					peopleId=${projectList.pmanagerId}&projectId=${projectList.projectId}">
						<span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;发送提醒</a>
				</td>
				</tr>
		</c:forEach>
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
						href="${pageContext.request.contextPath}/dmanager?method=showInfo&currentPage=${pageBean.currentPage-1 }"
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
						href="${pageContext.request.contextPath}/dmanager?method=showInfo&currentPage=${page }">${page }</a></li>
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
					href="${pageContext.request.contextPath}/dmanager?method=showInfo&currentPage=${pageBean.currentPage+1 }"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
			</ul>
			</div>
		<!-- 分页结束 -->
	
	</div>
</body>
</html>