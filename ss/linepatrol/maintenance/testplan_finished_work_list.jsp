<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 toViewForm=function(idValue,isread){
            window.location.href = "${ctx}/testPlanAction.do?method=viewPaln&planId="+idValue;
		}
		</script>
		<title>测试计划一览表</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		 <bean:size id="size"  name="handeledPlans" />
		<template:titile value="已办测试计划一览表  (<font color='red'>${size}条已办</font>)" />
		<logic:notEmpty name="handeledPlans">
		<display:table name="sessionScope.handeledPlans" id="currentRowObject" pagesize="15">
			<bean:define id="id" name="currentRowObject" property="id"></bean:define>
		 	<bean:define id="testState" name="currentRowObject" property="test_state"></bean:define>
		 	<bean:define name="currentRowObject" property="creator_id" id="sendUserId" />
			<c:if test="${LOGIN_USER.deptype eq 1}">
				<display:column property="contractorname" sortable="true" title="代维单位" headerClass="subject" />
			</c:if>
			 <display:column media="html" title="计划名称" >
				<a href="javascript:toViewForm('<%=id%>')"><bean:write name="currentRowObject" property="test_plan_name"/></a>
            </display:column>
			<display:column property="plantime" sortable="true" title="测试计划时间" headerClass="subject" />
			<display:column property="plantype" sortable="true" title="测试类型" headerClass="subject"/>
			<display:column property="plannum" sortable="true" title="计划测试数量" headerClass="subject"/>
			<display:column property="testnum" sortable="true" title="实际测试数量" headerClass="subject"/>
			<display:column property="state" sortable="true" title="状态" headerClass="subject" />
			<display:column media="html" title="操作" >
				<a href="javascript:toViewForm('<%=id %>','isread')">查看</a>
            </display:column>
		</display:table>
		</logic:notEmpty>
	</body>
</html>
