<%--显示待回复的任务列表--%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
			
		<style type="text/css">
			.subject{text-align:center;}
		</style>
		
		<script type="text/javascript">
			function addOneForm(id, type) {
				
			}
			
			function restoreTask(id,type) {
				var url="PollingTaskAction.do?method=showOneForRestore&id="+id+"&type="+type;
				window.location.href=url;
			}
			
			function getOneForm(id,type) {
				var flag = 3;
				var url="PollingTaskAction.do?method=showOneListInfo&id="+id+"&type="+type+"&flag="+flag;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="待回复机房巡检任务一览表"/>
		<%
			BasicDynaBean object = null;
			String id = null;
			String type = null;
			String num = null;
			String title = null;
		 %>
		<display:table name="sessionScope.taskList" requestURI="${ctx}/MobileTaskAction.do?method=showTaskForRestore" id="currentRowObject" pagesize="18">
			<%
				object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				if(object != null) {
					id = (String)object.get("tid");
					type = (String)object.get("type");
					num = (String)object.get("numerical");
					title = (String)object.get("tasktitle");
				}
			%>
			<display:column media="html" title="序列号" class="subject" headerClass="subject">
				<a href="javascript:getOneForm('<%=id %>','<%=type %>')"><%=num %></a>
			</display:column>
			<display:column media="html" title="主题" class="subject" headerClass="subject">
				<a href="javascript:getOneForm('<%=id %>','<%=type %>')"><%=title %></a>
			</display:column>
			<display:column property="conname" sortable="true" title="代维公司" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="exename" sortable="true" title="执行人" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="exetime" sortable="true" title="执行日期" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="checkname" sortable="true" title="检查人" headerClass="subject" maxLength="20" class="subject"/>
			<display:column media="html" title="操作" class="subject" headerClass="subject">
				<a href="javascript:restoreTask('<%=id %>','<%=type %>')">回复任务</a>
			</display:column>
			
		</display:table>
	</body>	
	
</html>