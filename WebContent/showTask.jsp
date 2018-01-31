<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改员工任务</title>
<script src="js/jquery.qrcode.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<!-- 引入表单校验jquery插件 -->
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />
</head>
<body background="img/background.jpg">

<jsp:include page="/header.jsp"></jsp:include>
	<div class="container"style="width: 100%;">
	<div>
		<table border="1" class="table table-hover">
			<tr>
				<td><h5>员工编号&nbsp;&nbsp;&nbsp;${modifyEmp.peopleId}</h5></td>
				<td rowspan="2" style="width:100px">
					<div align="center">
						<img alt="头像" src="${pageContext.request.contextPath }/${modifyEmp.photo}" 
							class="img-circle" style=" width:100px; height:100px;" >
					</div>
				</td>
			</tr>
			<tr>
				<td><h5>员工姓名&nbsp;&nbsp;&nbsp;${modifyEmp.peopleName}</h5>
				</td>
			</tr>
		</table>
	</div>
		<table border="1" class="table table-hover">
				<tr>
					<td width="20%">项目名称</td>
					<td width="20%">项目内容</td>
					<td width="20%">任务内容</td>
					<td width="20%">任务进度</td>
					<td width="10%">功能1</td>
					<td width="10%">功能2</td>
				</tr>
					<c:forEach var="taskList" items="${taskList}">
						<tr>
							<td>${taskList.project.projectName}</td>
							<td>${taskList.project.projectContent}</td>
							<td>${taskList.taskName}</td>
							<td>
							    <div class="progress progress-striped active">
								<div class="progress-bar progress-bar-success" role="progressbar"
									aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
									style="width: ${taskList.taskProgress};">
									<span  style="color:blue;">${taskList.taskProgress} 完成</span>
								</div>
								</div>
							</td>
							<td><a class="btn btn-warning btn-sm" role="button"
								href="${pageContext.request.contextPath }/pmanager?method=getModifyMemberTask&peopleId=${modifyEmp.peopleId}&taskId=${taskList.taskId}">
								<span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改任务</a>
							</td>
							<td><a class="btn btn-danger btn-sm" role="button"
								href="${pageContext.request.contextPath }/pmanager?method=deleteMemberTask&taskId=${taskList.taskId}" onclick="return foo();">
								<span class="glyphicon glyphicon-trash"></span>&nbsp;&nbsp;删除任务</a>
							</td>
						</tr>
						
					</c:forEach>

		</table>
	<a class="btn btn-default btn-sm" role="button" style="float:right"
	   href="${pageContext.request.contextPath }/pmanager">
	   <span class="glyphicon glyphicon-arrow-left"></span>&nbsp;&nbsp;返回</a>
	</div>


</body>
</html>