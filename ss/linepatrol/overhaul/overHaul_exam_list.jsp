<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>大修项目考核查询</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<template:titile value="考核查询" />
		<html:form action="/overHaulExamAction.do?method=examList"
			styleId="submitForm1">
			<table border="1" align="center" cellspacing="0" cellpadding="1"
				class="tabout" width="80%">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						项目名称：
					</td>
					<td class="tdulright">
						<html:text property="projectName"></html:text>
					</td>
				</tr>
			</table>
			<div align="center">
				<html:submit property="action" styleClass="button">查询</html:submit>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="submitForm1"></template:displayHide>
		<logic:notEmpty name="examList">
			<script type="text/javascript">
				toExamDispatch=function(taskId,applyId,contractorId){
	            	window.location.href = "${ctx}/overHaulExamAction.do?method=examOverHaulForm&id="+taskId+"&applyId="+applyId+"&contractorId="+contractorId;
				};
			</script>
			<%
				BasicDynaBean object = null;
				String applyId = "";
				String contractorId = "";
				String taskId = "";
			%>
			<display:table name="sessionScope.examList" id="examBean" pagesize="18">
				<%
					object = (BasicDynaBean) pageContext.findAttribute("examBean");
					applyId = object.get("apply_id") == null ? "" : (String)object.get("apply_id");
					contractorId = object.get("contractorid") == null ? "" : (String)object.get("contractorid");
					taskId = object.get("task_id") == null ? "" : (String)object.get("task_id");
				%>
				<display:column property="project_name" title="项目名称"
					headerClass="subject" sortable="true" />
				<display:column property="username" title="立项人"
					headerClass="subject" sortable="true" />
				<display:column property="budget_fee" title="预算费用（元）"
					headerClass="subject" sortable="true" />
				<display:column property="fee" title="使用费用（元）"
					headerClass="subject" sortable="true" />
				<display:column property="start_time" title="开始时间"
					headerClass="subject" sortable="true" />
				<display:column property="end_time" title="结束时间"
					headerClass="subject" sortable="true" />
				<display:column property="contractorname" title="处理单位"
					headerClass="subject" sortable="true" />
				<display:column media="html" title="操作" >
					<a href="javascript:toExamDispatch('<%=taskId %>','<%=applyId %>','<%=contractorId %>')">考核</a>
				</display:column>
			</display:table>
			<div style="height: 50px; text-align: center;">
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</logic:notEmpty>
	</body>
</html>
