<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>������ѯ�б�</title>
		<script type="text/javascript">
			toViewDrillPlanModify=function(planModifyId){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModify&planModifyId="+planModifyId;
			}
		</script>
	</head>
	<%
		BasicDynaBean object = null;
		Object modifyId = null;
		Object taskName = null;
	%>
	<body>
		<template:titile value="����������ֹ�б�"/>
		<display:table name="sessionScope.list" id="modify" pagesize="18">
			<display:column title="��������" media="html"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("modify");
	            if(object != null) {
	               modifyId = object.get("modify_id");
	               taskName = object.get("task_name");
				} %>
      			<a href="javascript:toViewDrillPlanModify('<%=modifyId%>')"><%=taskName%></a> 
			</display:column>
			<display:column property="prev_starttime" title="���ǰ��ʼʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="prev_endtime" title="���ǰ����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="next_starttime" title="�����ʼʱ��" headerClass="subject" maxLength="18" sortable="true"/>
			<display:column property="next_endtime" title="��������ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="modify_date" title="����ʱ��" headerClass="subject"  sortable="true"/>
		</display:table>
	</body>
</html>

