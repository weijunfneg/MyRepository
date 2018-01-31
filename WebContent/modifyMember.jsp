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

<title>修改员工信息</title>
</head>
<body background="img/background.jpg">
	<jsp:include page="/header.jsp"></jsp:include>
	<div class="container"style="width: 40%;">
	<form action="${pageContext.request.contextPath }/modifyMember" method="post" enctype="multipart/form-data">
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
			<tr><td><a>员工密码</a><input type="password" name="password"  value="${modifyEmp.password}"/></td></tr>
			<tr>
				<td><input type="file" name="photo"  value="修改头像 "/></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit"  value="提交"/>
			</tr>
		</table>
	</form>
	<a class="btn btn-default btn-sm" role="button" style="float:right"
	   href="${pageContext.request.contextPath }/pmanager">
	   <span class="glyphicon glyphicon-arrow-left"></span>&nbsp;&nbsp;返回</a>
	</div>
</body>
</html>