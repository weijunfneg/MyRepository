<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.iflytek.domain.People"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/jquery.qrcode.min.js"></script>
<script src="js/echarts.min.js"></script>
<link rel="stylesheet" href="css/jquery-ui.structure.min.css" />
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/jquery-ui.min.css" />
<link rel="stylesheet" href="css/jquery-ui.theme.min.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

<title>项目经理功能列表</title>


<script type="text/javascript">

	function foo() {
		if (confirm("确认删除吗？")) {
			return true;
		}
		return false;
	}

	$(function() {
		$("#myAlert").bind('closed.bs.alert', function() {
			document.location.href = "${pageContext.request.contextPath }/pmanager?method=removeMessage";
		});
	});

	$(function() {
		$("#tags").autocomplete({
			source : function(request, response) {

				$.ajax({
					url : "${pageContext.request.contextPath }/pmanager",
					async : true,
					type : "POST",
					data : {
						"method" : "autoSearchWord",
						"search" : request.term
					},
					success : function(availableTags) {
						response(availableTags);
					},
					dataType : "json"
				});
			}
		});
	});"searchChart"

	$(document).ready(function() {
		$("#searchChart").click(function() {
			$.ajax({
				url : "${pageContext.request.contextPath }/pmanager",
				async : true,
				type : "POST",
				data : {
					"method" : "searchChart"
				},
				success : function(project) {
					$("#modal-body").empty();
					$("#modal-body").append("<div id='main' style='width: 550px;height:350px;'></div>");
					// 基于准备好的dom，初始化echarts实例
					var myChart = echarts.init(document.getElementById('main'));
					// 指定图表的配置项和数据
					var option = {
						title : {
							text : '项目进度统计'
						},
						tooltip : {},
						legend : {
							data : [ '完成量' ]
						},
						xAxis : {
							//data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
							data : project.projectName
						},
						yAxis : {},
						series : [ {
							name : '完成量',
							type : 'bar',
							data : project.totalProgress
						//data: [5, 20, 36, 10, 10, 20]
						} ]
					};
					// 使用刚指定的配置项和数据显示图表。
					myChart.setOption(option);
				},
				dataType : "json"
			});
		});
	});


	$(document).ready(function() {
		$("#searchMember").click(function() {
			var pname = $("#tags").val();
			$.ajax({
				url : "${pageContext.request.contextPath }/pmanager",
				async : true,
				type : "POST",
				data : {
					"method" : "searchMember",
					"pname" : pname
				},
				success : function(people) {
					$("#modal-body").empty();
					$("#modal-body").append("<table border='1' class='table table-hover'>" +
						"<tr><td width='20%'>员工编号</td><td width='20%'>员工姓名</td><td width='15%'>查看详情</td><td width='15%'>功能1</td><td width='15%'>功能2</td>" +
						"<td width='15%'>功能3</td></tr>" +
						"<tr><td>" + people.peopleId + "</td><td>" + people.peopleName + "</td><td><a class='btn btn-info btn-sm' role='button'" +
						"href='${pageContext.request.contextPath }/pmanager?method=showTask&peopleId=" + people.peopleId + "'>" +
						"<span class='glyphicon glyphicon-search'></span>&nbsp;&nbsp;查看详情</a></td>" +
						"<td><a class='btn btn-primary btn-sm' role='button'" +
						"href='${pageContext.request.contextPath }/pmanager?method=getAddMemberTask&peopleId=" + people.peopleId + "'>" +
						"<span class='glyphicon glyphicon-plus'></span>&nbsp;&nbsp;添加任务</a></td>" +
						"<td><a class='btn btn-warning btn-sm' role='button'" +
						"href='${pageContext.request.contextPath }/pmanager?method=getModifyMember&peopleId=" + people.peopleId + "'>" +
						"<span class='glyphicon glyphicon-edit'></span>&nbsp;&nbsp;修改员工信息</a></td>" +
						"<td><a class='btn btn-danger btn-sm' role='button'" +
						"href='${pageContext.request.contextPath }/pmanager?method=deleteMember&peopleId=" + people.peopleId + "' onclick='return foo();'>" +
						"<span class='glyphicon glyphicon-trash'></span>&nbsp;&nbsp;删除员工</a></td></tr></table>");

				},
				dataType : "json"
			});
		});
	});
</script>


</head>
<body background="img/background.jpg">
<body bgcolor="#FFC1C1">
	<jsp:include page="/header.jsp"></jsp:include>
	<c:if test="${!empty people.message}">
		<div id="myAlert" class="alert alert-warning">
			<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>${people.message}</strong>。
		</div>
	</c:if>

	<ul id="myTab" class="nav nav-tabs" align="center">
		<li class="active"><a href="#project" data-toggle="tab">项目管理</a>
		</li>
		<li><a href="#emp" data-toggle="tab">成员管理</a></li>
	</ul>


	<div id="myTabContent" class="tab-content" align="center">
		<div class="tab-pane fade in active" id="project">
			<a class="btn btn-primary btn-sm"
				href="${pageContext.request.contextPath }/addProject.jsp"
				role="button"><span class="glyphicon glyphicon-plus"></span>添加项目</a>
			<button type="submit" class="btn btn-primary btn-sm"
				data-toggle="modal" data-target="#myModal" id="searchChart">
				<span class="glyphicon glyphicon-stats"></span>&nbsp;数据统计
			</button>
			<table border="1" class="table table-hover">
				<tr>
					<td width="20%">项目名称</td>
					<td width="25%">项目内容</td>
					<td width="25%">项目进度</td>
					<td width="15%">功能1</td>
					<td width="15%">功能2</td>
				</tr>
				<c:forEach items="${pageBean.list }" var="projectList">
					<tr>
						<td>${projectList.projectName}</td>
						<td>${projectList.projectContent}</td>
						<td>
							<div class="progress progress-striped active">
								<div class="progress-bar progress-bar-success"
									role="progressbar" aria-valuenow="60" aria-valuemin="0"
									aria-valuemax="100" style="width: ${projectList.totalProgress}%;">
									<span style="color:blue;">${projectList.totalProgress}%完成</span>
								</div>
							</div>
						</td>
						<td><a class="btn btn-warning btn-sm" role="button"
							href="${pageContext.request.contextPath }/pmanager?method=getModifyProject&projectId=${projectList.projectId}">
								<span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改项目
						</a></td>
						<td><a class="btn btn-danger btn-sm" role="button"
							href="${pageContext.request.contextPath }/pmanager?method=deleteProject&projectId=${projectList.projectId}"
							onclick="return foo();"> <span class="glyphicon glyphicon-trash"></span>&nbsp;&nbsp;删除项目
						</a></td>
					</tr>
				</c:forEach>
			</table>
			<!--分页 -->
			<div style="width: 380px; margin: 0 auto; margin-top: 20px;text-align:center;">
				<ul class="pagination" style="text-align: center; margin-top: 0px;">
				<!-- 上一页 -->
				<c:if test="${pageBean.currentPage==1 }">
					<li class="disabled"><a href="javascript:void(0);"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:if test="${pageBean.currentPage!=1 }">
					<li><a
						href="${pageContext.request.contextPath}/pmanager?method=showInfo&currentPage=${pageBean.currentPage-1 }"
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
							href="${pageContext.request.contextPath}/pmanager?method=showInfo&currentPage=${page }">${page }</a></li>
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
						href="${pageContext.request.contextPath}/pmanager?method=showInfo&currentPage=${pageBean.currentPage+1 }"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
				</ul>
			</div>
			<!-- 分页结束 -->
		</div>

		<!-- 成员管理 -->
		<diV class="tab-pane fade" id="emp">
			<nav class="nav navbar-nav navbar-left" role="navigation" style="padding-left:40%">
			<div style="float: left;margin-top: 7px">
				<a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath }/pmanager?method=addMember"
					role="button"><span class="glyphicon glyphicon-plus"></span>添加员工</a>
			</div>
			<div class="navbar-header"></div>
			<div class="ui-widget" style="float: left ;">
				<label for="tags"></label>
				<input type="text" id="tags" class="form-control" placeholder="请输入姓名">
			</div>
			<div style="float: right;margin-top: 5px">
				<button type="submit" class="btn btn-success" data-toggle="modal"
					data-target="#myModal" id="searchMember">
					<span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;搜索
				</button>
			</div>
			</nav>
			<table border="1" class="table table-hover">
				<tr>
					<td width="20%">员工编号</td>
					<td width="20%">员工姓名</td>
					<td width="15%">查看详情</td>
					<td width="15%">功能1</td>
					<td width="15%">功能2</td>
					<td width="15%">功能3</td>
				</tr>
				<c:forEach items="${peoplePageBean.list }" var="peopleList">
					<tr>
						<td>${peopleList.peopleId}</td>
						<td>${peopleList.peopleName}</td>
						<td><a class="btn btn-info btn-sm" role="button"
							href="${pageContext.request.contextPath }/pmanager?method=showTask&peopleId=${peopleList.peopleId}">
								<span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;查看详情
						</a></td>
						<td><a class="btn btn-primary btn-sm" role="button"
							href="${pageContext.request.contextPath }/pmanager?method=getAddMemberTask&peopleId=${peopleList.peopleId}">
								<span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;添加任务
						</a></td>
						<td><a class="btn btn-warning btn-sm" role="button"
							href="${pageContext.request.contextPath }/pmanager?method=getModifyMember&peopleId=${peopleList.peopleId}">
								<span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改员工信息
						</a></td>
						<td><a class="btn btn-danger btn-sm" role="button"
							href="${pageContext.request.contextPath }/pmanager?method=deleteMember&peopleId=${peopleList.peopleId}"
							onclick="return foo();"> <span class="glyphicon glyphicon-trash"></span>&nbsp;&nbsp;删除员工
						</a></td>
					</tr>
				</c:forEach>
			</table>
			<!--分页 -->
			<div style="width: 380px; margin: 0 auto; margin-top: 20px;text-align:center;">
				<ul class="pagination" style="text-align: center; margin-top: 0px;">
				<!-- 上一页 -->
				<c:if test="${peoplePageBean.currentPage==1 }">
					<li class="disabled"><a href="javasript:void(0);"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:if test="${peoplePageBean.currentPage!=1 }">
					<li><a
						href="${pageContext.request.contextPath}/pmanager?method=showInfo&peopleId=${peopleId}&currentPage=${pageBean.currentPage-1 }"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span> </a></li>
				</c:if>
				<!-- 显示每一页 -->
				<c:forEach begin="1" end="${peoplePageBean.totalPage }" var="page">
				<!-- 判断是否是当前页 -->
				<c:if test="${page==peoplePageBean.currentPage }">
					<li class="active"><a href="javascript:void(0);">${page }</a></li>
				</c:if>
				<c:if test="${page!=peoplePageBean.currentPage }">
					<li><a href="${pageContext.request.contextPath}/pmanager?method=showInfo&currentPage=${page }">${page }</a></li>
				</c:if>
				</c:forEach>
				<!-- 下一页 -->
				<c:if test="${peoplePageBean.currentPage==peoplePageBean.totalPage }">
					<li c lass="disabld"><a href="javascript:void(0);"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
				</c:if>
				<c:if test="${peoplePageBean.currentPage!=peoplePageBean.totalPage }">
				<li><a
					href="${pageContext.request.contextPath}/pmanager?method=showInfo&currentPage=${pageBean.currentPage+1 }"
					aria-label="Next"> <span aria-hi dden="true">&raquo;</span>
				</a></li>
				</c:if>
				</ul>
			</div>
			<!-- 分页结束 -->
		</diV>	

		<!-- 态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true" style="width: 100%">
			<div class="modal-dialog" style="width: 100%">
				<div class="modal-content" style="width: 100%">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">员工信息</h4>
					</div>
					<div class="modal-body" id="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<!--<button type="button" class="btn btn-primary">提交更改</button> -->
					</div>
				</div>
			</div>
		</div>
		<!-- /.modal-->
	</div>
</body>
</html>