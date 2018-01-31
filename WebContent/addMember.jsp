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

<title>添加员工</title>


<style>
.error {
	color: red;
	font-size: 12px;
}
</style>

<script type="text/javascript">
	$.validator.addMethod(
		"isExit",
		function(value, element) {
			var flag = false;
			$.ajax({  
		        "async":false,  
		        "url":"${pageContext.request.contextPath }/pmanager?method=peopleIdIsExit",  
		        "data":{"peopleId":value},  
		        "type":"POST",  
		        "dataType":"json", 
		        "success" : function(data) {  
		            flag = data.isExist;
		        }
		    });
		return !flag;
	});

	
	$(function() {
		$("#myform").validate({
			rules : {
			"peopleId" : {
				"required" : true,
				"isExit":	true
			},
			"peopleName" : {
				"required" : true
			},
			"password" : {
				"required" : true,
			}
		},
		messages : {
			"peopleId" : {
				"required" : "员工编号不能为空",
				"isExit":"该员工编号已存在"
			},
			"peopleName" : {
				"required" : "请输入成员姓名  ",
			},
			"password" : {
				"required" : "请输入成员密码  ",
			}
		}
		});
	});

</script>


</head>



<body background="img/background.jpg">
	<jsp:include page="/header.jsp"></jsp:include>
	<div class="container"style="width: 40%;">
	
	<form id="myform" action="${pageContext.request.contextPath }/addMember" method="post" 
	 enctype="multipart/form-data"  class="form-horizontal">
			<div class="form-group">
				<label for="peopleId" class="col-sm-2 control-label">员工编号</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="peopleId" name="peopleId"
						placeholder="员工编号" value="em">
				</div>
			</div>
			
			<div class="form-group">
				<label for="peopleName" class="col-sm-2 control-label">员工姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="peopleName" name="peopleName"
						placeholder="员工姓名">
				</div>
			</div>
			
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">员工密码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="password" name="password"
						placeholder="员工密码">
				</div>
			</div>
			
			<div class="form-group">
				<label for="photo" class="col-sm-2 control-label">上传头像</label> 
				<div class="col-sm-10">
					<input type="file" name="photo" id="photo">
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" width="100" value="提交" name="submit" id="submit"
						style="background: red ; height: 35px; width: 100px; color: white;">
				</div>
			</div>
	</form>
	<a class="btn btn-default btn-sm" role="button" style="float:right"
	   href="${pageContext.request.contextPath }/pmanager">
	   <span class="glyphicon glyphicon-arrow-left"></span>&nbsp;&nbsp;返回</a>
	</div>
	</div>
</body>
</html>