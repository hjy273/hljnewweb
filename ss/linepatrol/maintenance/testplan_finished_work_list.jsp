<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 toViewForm=function(idValue,isread){
            window.location.href = "${ctx}/testPlanAction.do?method=viewPaln&planId="+idValue;
		}
		</script>
		<title>���Լƻ�һ����</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		 <bean:size id="size"  name="handeledPlans" />
		<template:titile value="�Ѱ���Լƻ�һ����  (<font color='red'>${size}���Ѱ�</font>)" />
		<logic:notEmpty name="handeledPlans">
		<display:table name="sessionScope.handeledPlans" id="currentRowObject" pagesize="15">
			<bean:define id="id" name="currentRowObject" property="id"></bean:define>
		 	<bean:define id="testState" name="currentRowObject" property="test_state"></bean:define>
		 	<bean:define name="currentRowObject" property="creator_id" id="sendUserId" />
			<c:if test="${LOGIN_USER.deptype eq 1}">
				<display:column property="contractorname" sortable="true" title="��ά��λ" headerClass="subject" />
			</c:if>
			 <display:column media="html" title="�ƻ�����" >
				<a href="javascript:toViewForm('<%=id%>')"><bean:write name="currentRowObject" property="test_plan_name"/></a>
            </display:column>
			<display:column property="plantime" sortable="true" title="���Լƻ�ʱ��" headerClass="subject" />
			<display:column property="plantype" sortable="true" title="��������" headerClass="subject"/>
			<display:column property="plannum" sortable="true" title="�ƻ���������" headerClass="subject"/>
			<display:column property="testnum" sortable="true" title="ʵ�ʲ�������" headerClass="subject"/>
			<display:column property="state" sortable="true" title="״̬" headerClass="subject" />
			<display:column media="html" title="����" >
				<a href="javascript:toViewForm('<%=id %>','isread')">�鿴</a>
            </display:column>
		</display:table>
		</logic:notEmpty>
	</body>
</html>
