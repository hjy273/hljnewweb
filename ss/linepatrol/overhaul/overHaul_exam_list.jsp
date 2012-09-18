<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>������Ŀ���˲�ѯ</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<template:titile value="���˲�ѯ" />
		<html:form action="/overHaulExamAction.do?method=examList"
			styleId="submitForm1">
			<table border="1" align="center" cellspacing="0" cellpadding="1"
				class="tabout" width="80%">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						��Ŀ���ƣ�
					</td>
					<td class="tdulright">
						<html:text property="projectName"></html:text>
					</td>
				</tr>
			</table>
			<div align="center">
				<html:submit property="action" styleClass="button">��ѯ</html:submit>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">��д</html:reset>
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
				<display:column property="project_name" title="��Ŀ����"
					headerClass="subject" sortable="true" />
				<display:column property="username" title="������"
					headerClass="subject" sortable="true" />
				<display:column property="budget_fee" title="Ԥ����ã�Ԫ��"
					headerClass="subject" sortable="true" />
				<display:column property="fee" title="ʹ�÷��ã�Ԫ��"
					headerClass="subject" sortable="true" />
				<display:column property="start_time" title="��ʼʱ��"
					headerClass="subject" sortable="true" />
				<display:column property="end_time" title="����ʱ��"
					headerClass="subject" sortable="true" />
				<display:column property="contractorname" title="����λ"
					headerClass="subject" sortable="true" />
				<display:column media="html" title="����" >
					<a href="javascript:toExamDispatch('<%=taskId %>','<%=applyId %>','<%=contractorId %>')">����</a>
				</display:column>
			</display:table>
			<div style="height: 50px; text-align: center;">
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</logic:notEmpty>
	</body>
</html>
