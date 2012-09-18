<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>演练查询列表</title>
		<script type="text/javascript">
			toViewDrillSummary=function(summaryId){
            	window.location.href = "${ctx}/drillSummaryAction.do?method=viewDrillSummary&summaryId="+summaryId;
			}
			toExamDrill=function(summaryId){
            	window.location.href = "${ctx}/drillExamAction.do?method=examDrillForm&summaryId="+summaryId;
			}
		</script>
	</head>
	<%
		BasicDynaBean object=null;
		Object taskId=null;
		Object planId=null;
		Object summaryId=null;
		Object taskName= null;
	%>
	<body>
		<template:titile value="演练查询列表"/>
		<display:table name="sessionScope.list" id="drill" pagesize="18">
			<bean:define id="applyState" name="drill" property="drill_state"></bean:define>
			<display:column title="演练名称" media="html"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("drill");
	            if(object != null) {
	               taskId = object.get("task_id");
	               taskName = object.get("task_name");
				} %>
      			<a href="javascript:toViewDrillTask('<%=taskId%>')"><%=taskName%></a> 
			</display:column>
			<display:column property="drill_level" title="演练级别" headerClass="subject"  sortable="true"/>
			<display:column property="task_begintime" title="计划开始时间" headerClass="subject"  sortable="true"/>
			<display:column property="task_endtime" title="计划结束时间" headerClass="subject"  sortable="true"/>
			<display:column property="plan_person_number" title="计划投入人数" headerClass="subject"  sortable="true"/>
			<display:column property="plan_car_number" title="计划投入车辆数" headerClass="subject"  sortable="true"/>
			<display:column property="summary_person_number" title="实际投入人数" headerClass="subject"  sortable="true"/>
			<display:column property="summary_car_number" title="实际投入车辆数" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toViewDrillSummary('<bean:write name="drill" property="summary_id"/>')" title="查看演练总结">查看</a> |
				<a href="javascript:toExamDrill('<bean:write name="drill" property="summary_id"/>')" title="演练评分">评分</a>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<c:if test="${applyState=='1'}">
						| <a href="javascript:toCancelForm('<%=id%>')">取消</a>
					</c:if>
					<c:if test="${applyState=='2'}">
						| <a href="javascript:toCancelForm('<%=id%>')">取消</a>
					</c:if>
					<c:if test="${applyState=='3'}">
						| <a href="javascript:toCancelForm('<%=id%>')">取消</a>
					</c:if>
				</c:if>
			</display:column>
		</display:table>
	</body>
</html>

