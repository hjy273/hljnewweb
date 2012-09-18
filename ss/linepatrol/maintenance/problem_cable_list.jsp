<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			handelProblemForm=function(id,act){
			window.location.href="${ctx}/problemCableAction.do?method=handleProblemForm&pid="+id+"&act="+act;
			//self.location.replace(url);
		}
				
				
		exportList=function(){
			var url="${ctx}/testPlanQueryStatAction.do?method=exportStatTestPlans";
			self.location.replace(url);
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
		<%	int i = 1; %>
		 <bean:size id="size"  name="problems" />
		<template:titile value="问题光缆一览表  (<font color='red'>共${size}条</font>)" />
		<display:table name="sessionScope.problems" id="currentRowObject" pagesize="15">
	    	<logic:notEmpty name="problems">
			    <bean:define id="pid" name="currentRowObject" property="id"></bean:define>
				<display:column value="<%=i%>" title="序号" style="5%"></display:column>
				<display:column media="html" title="计划名称" >
					<a href="javascript:handelProblemForm('<%=pid%>','view');"><bean:write name="currentRowObject" property="test_plan_name"/></a>
	            </display:column>
				<display:column property="segmentname" sortable="true" title="中继段名称" headerClass="subject" maxLength="25"/>
				<display:column property="problem_description" sortable="true" title="问题描述" headerClass="subject" maxLength="25"/>
				<display:column property="tester" sortable="true" title="测试人" headerClass="subject" maxLength="25"/>
				<display:column property="state" sortable="true" title="状态" headerClass="subject" />
				<display:column media="html" title="操作" >
					<% i++;	%>
					<logic:equal value="2" name="LOGIN_USER" property="deptype">
		            	<a href="javascript:handelProblemForm('<%=pid%>','')">处理记录</a>
		            </logic:equal>
	            </display:column>
           </logic:notEmpty>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="ffff">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
