<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>演练查询列表</title>
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
		<template:titile value="演练方案终止列表"/>
		<display:table name="sessionScope.list" id="modify" pagesize="18">
			<display:column title="演练名称" media="html"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("modify");
	            if(object != null) {
	               modifyId = object.get("modify_id");
	               taskName = object.get("task_name");
				} %>
      			<a href="javascript:toViewDrillPlanModify('<%=modifyId%>')"><%=taskName%></a> 
			</display:column>
			<display:column property="prev_starttime" title="变更前开始时间" headerClass="subject"  sortable="true"/>
			<display:column property="prev_endtime" title="变更前结束时间" headerClass="subject"  sortable="true"/>
			<display:column property="next_starttime" title="变更后开始时间" headerClass="subject" maxLength="18" sortable="true"/>
			<display:column property="next_endtime" title="变更后结束时间" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="创建人" headerClass="subject"  sortable="true"/>
			<display:column property="modify_date" title="创建时间" headerClass="subject"  sortable="true"/>
		</display:table>
	</body>
</html>

