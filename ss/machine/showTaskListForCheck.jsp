<!--显示所有已完成的任务 -->
<%@page import="com.cabletech.machine.beans.MobileTaskBean" %>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
			.subject{text-align:center;}
		</style>
		<script type="text/javascript">
			getOneForm=function(id,type) {
				var flag = 4;
				var url="PollingTaskAction.do?method=showOneListInfo&id="+id+"&type="+type+"&flag="+flag;
				window.location.href=url;
			}
			
			function checkTask(tid,type) {
				var url = "PollingTaskAction.do?method=showOneForCheck&tid="+tid+"&type="+type;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<%
			BasicDynaBean object = null;
			String id = null;
			String type = null;
			String num = null;
			String title = null;
			String userid = null;
		 %>
		<template:titile value="任务核查信息一览表"/>
		<display:table name="sessionScope.taskList" requestURI="${ctx}/MobileTaskAction.do?method=showTaskForCheck" id="currentRowObject" pagesize="18">
			<%
				object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				
				if(object != null) {
					id = (String)object.get("tid");
					type = (String)object.get("type");
					num = (String)object.get("numerical");
					title = (String)object.get("tasktitle");
					userid = (String)object.get("checkuser");
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
				<% 
					 UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
                     try{
                     		String currentUserID =  userinfo.getUserID();
                     		if(currentUserID.equals(userid)){
                   
				%>
				<a href="javascript:checkTask('<%=id %>','<%=type %>')">核查任务</a>
				<%
					 } }catch(Exception e)
                     {
                     }
				 %>
				
			</display:column>
			
		</display:table>
		
	</body>
</html>