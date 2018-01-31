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

<title>登录</title>


<style>
.error {
	color: red;
	font-size: 12px;
}
</style>
<script type="text/javascript">

	function checkImage(obj){
		obj.src="${pageContext.request.contextPath }/login?method=sendVerifyCode&time="+new Date().getTime();
	}

	
	
	$.validator.addMethod(
		"islogincode",
		function(value, element) {
			var flag = false;
			$.ajax({  
		        "async":false,  
		        "url":"${pageContext.request.contextPath }/login?method=verificationCode",  
		        "data":{"logincode":value},  
		        "type":"POST",  
		        "dataType":"json", 
		        "success" : function(data) {  
		            flag = data.isExist;
		        }
		    });
		return flag;
	});
	
	
	$.validator.addMethod(
		"isPeople",
		function(value, element) {
			var flag = false;
			$.ajax({
				"async" : false,
				"url" : "${pageContext.request.contextPath}/login?method=isPeopleId",
				"data" : {"peopleId" : value},
				"type" : "POST",
				"dataType" : "json",
				"success" : function(data) {
					flag = data.isExist;
				}
			});
			return flag;
		});
	
	$(function() {
		$("#loginform").validate({
			rules : {
				"password" : {
					"required" : true,
					"rangelength" : [ 1, 16 ]
				},
				"peopleId" : {
					"required" : true,
					"isPeople" : true
				},
				"logincode" : {
					"required" : true,
					"islogincode":true
				}
				
			},
			messages : {
				"password" : {
					"required" : "密码不能为空",
					"rangelength" : "密码长度1-16位"
				},
				"peopleId" : {
					"required" : "请输入用户id ",
					"isPeople" : "不存在该用户 "
				},
				"logincode" : {
					"required" : "请输入验证码",
					"islogincode":"验证码不符"
				}
				
			}
		});
	});
</script>


</head>
<body background="img/background.jpg"> 
	<div align="center" style="padding-top:20px">
	<h3>欢迎使用项目进度管理系统，请登录</h3>
	<div class="container"style="width: 40%;">
		<div>
			<span style="color:red">${loginError}</span>
		</div>
	<form action="${pageContext.request.contextPath }/login?method=login"
			id="loginform" method="post" class="form-horizontal">
			 
			<div class="form-group">
				<label for="peopleId" class="col-sm-2 control-label">员工编号:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="peopleId"
							name="peopleId" placeholder="员工编号" 
							value="${cookie.cookie_peopleId.value}" >
				</div>
			</div>
			
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">员工密码:</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="password"
							name="password" placeholder="员工密码"
							value="${cookie.cookie_password.value}" >
				</div>
			</div>
			
			<div class="form-group">
				<label for="logincode" class="col-sm-2 control-label">验证码</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="logincode" name="logincode"
							placeholder="请输入验证码">
				</div>
				<div class="col-sm-3">
					<img  onclick="checkImage(this)" src="${pageContext.request.contextPath }/login?method=sendVerifyCode" width="70" height="35">
				</div>
			</div>
			
			<div class="form-group">
				<div class="checkbox">
					<label> <input type="checkbox" name="autoLogin"
						value="autoLogin"> 自动登录
					</label>
				</div>
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" width="100" value="提交" name="submit" id="submit"
						style="background: red ; height: 35px; width: 100px; color: white;">
				</div>
			</div>
			</form>
	</div>
	</div>
</body>
</html>