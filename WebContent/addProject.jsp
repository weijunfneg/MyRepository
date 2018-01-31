<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>展示</title>

<script src="js/jquery.qrcode.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<!-- 引入表单校验jquery插件 -->
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<title>添加项目</title>

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
		        "url":"${pageContext.request.contextPath }/pmanager?method=projectIsExit",  
		        "data":{"projectName":value},  
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
				"projectName" : {
				"required" : true,
				"isExit":true
			},
			
			"projectContent" : {
				"required" : true,
			}
			
		},
		messages : {
			"projectName" : {
				"required" : "项目名不能为空",
				"isExit":"该项目名称已存在 "
			},
			"projectContent" : {
				"required" : "请输入项目内容 ",
			}
		}
		});
	});

</script>



</head>
<body background="img/background.jpg">
	<jsp:include page="/header.jsp"></jsp:include>
		<div class="container"style="width: 40%;">
		<form id="myform" action="${pageContext.request.contextPath }/pmanager?method=addProject"
			 method="post" class="form-horizontal">
			<div class="form-group">
				<label for="projectName" class="col-sm-2 control-label">项目名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="projectName" name="projectName"
						placeholder="项目名称" >
				</div>
			</div>
			
			<div class="form-group">
				<label for="projectContent" class="col-sm-2 control-label">项目内容</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="projectContent" name="projectContent"
						placeholder="项目内容" >
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

</body>
</html>