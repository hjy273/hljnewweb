<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			handelProblemForm=function(id){
				window.location.href="${ctx}/problemCableAction.do?method=handleProblemForm&pid="+id+"&act=view";
			//self.location.replace(url);
		}
		</script>
		<title>问题光缆一览表</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<%
		int i = 1;
	 %>
		<bean:size id="size"  name="finishedProblems" />
		<template:titile value="已处理问题光缆一览表  (<font color='red'>共${size}条</font>)" />
		<display:table name="sessionScope.finishedProblems" id="currentRowObject" pagesize="15">
			<logic:notEmpty name="finishedProblems">
				<display:column media="html" title="计划名称" >
					<bean:define id="id" name="currentRowObject" property="id"></bean:define>
					<a href="javascript:handelProblemForm('<%=id%>');"><bean:write name="currentRowObject" property="test_plan_name"/></a>
	            </display:column>
				<display:column property="segmentname" sortable="true" title="中继段名称" headerClass="subject" maxLength="25"/>
				<display:column property="problem_description" sortable="true" title="问题描述" headerClass="subject" maxLength="25"/>
				<display:column property="tester" sortable="true" title="测试人" headerClass="subject" maxLength="10"/>
				<display:column property="state" sortable="true" title="状态" headerClass="subject" />
	            <% i++; %>
           </logic:notEmpty>
		</display:table>
	</body>
</html>
