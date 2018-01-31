<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加员工任务</title>
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
	<div class="container"style="width: 60%;">
	<form action="${pageContext.request.contextPath }/pmanager?method=addMemberTask&peopleId=${modifyEmp.peopleId}" method="post" >
		<table border="1" class="table table-hover">
			<tr>
				<td><a>员工编号</a><input readonly="readonly" name="peopleId"  value="${modifyEmp.peopleId}"/></td>
				<td rowspan="4" style="width:100px">
					<div align="center">
						<img alt="头像" src="${pageContext.request.contextPath }/${modifyEmp.photo}" 
							class="img-circle" style=" width:100px; height:100px;" >
					</div>
				</td>
			</tr>
			<tr><td><a>员工姓名</a><input readonly="readonly" name="peopleName"  value="${modifyEmp.peopleName}"/></td></tr>
			<tr>
				<td>所属项目
					<select name="projectId" class="form-control">
						<c:forEach var="projectList" items="${projectList}">
							<option value="${projectList.projectId}">${projectList.projectName}</option>
						</c:forEach>
					</select>
				</td>
			<tr>
			<tr>
				<td colspan="1"><a>员工任务</a>
					<input class="form-control" type="text" name="taskName"  value=""  />
				</td>
				<td colspan="1"><a>任务进度</a>
				<input readonly="readonly" name="taskProgress"  value="0.0%"/>
			</tr>
			<tr>
				<td colspan="1"><input type="submit" name="submit"  value="提交"/>
			</tr>
		</table>
	</form>
	<a class="btn btn-default btn-sm" role="button" style="float:right"
	   href="${pageContext.request.contextPath }/pmanager">
	   <span class="glyphicon glyphicon-arrow-left"></span>&nbsp;&nbsp;返回</a>
	</div>

</body>
</html>