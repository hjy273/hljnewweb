<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>������ѯ�б�</title>
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
		<template:titile value="������ѯ�б�"/>
		<display:table name="sessionScope.list" id="drill" pagesize="18">
			<bean:define id="applyState" name="drill" property="drill_state"></bean:define>
			<display:column title="��������" media="html"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("drill");
	            if(object != null) {
	               taskId = object.get("task_id");
	               taskName = object.get("task_name");
				} %>
      			<a href="javascript:toViewDrillTask('<%=taskId%>')"><%=taskName%></a> 
			</display:column>
			<display:column property="drill_level" title="��������" headerClass="subject"  sortable="true"/>
			<display:column property="task_begintime" title="�ƻ���ʼʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="task_endtime" title="�ƻ�����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="plan_person_number" title="�ƻ�Ͷ������" headerClass="subject"  sortable="true"/>
			<display:column property="plan_car_number" title="�ƻ�Ͷ�복����" headerClass="subject"  sortable="true"/>
			<display:column property="summary_person_number" title="ʵ��Ͷ������" headerClass="subject"  sortable="true"/>
			<display:column property="summary_car_number" title="ʵ��Ͷ�복����" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toViewDrillSummary('<bean:write name="drill" property="summary_id"/>')" title="�鿴�����ܽ�">�鿴</a> |
				<a href="javascript:toExamDrill('<bean:write name="drill" property="summary_id"/>')" title="��������">����</a>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<c:if test="${applyState=='1'}">
						| <a href="javascript:toCancelForm('<%=id%>')">ȡ��</a>
					</c:if>
					<c:if test="${applyState=='2'}">
						| <a href="javascript:toCancelForm('<%=id%>')">ȡ��</a>
					</c:if>
					<c:if test="${applyState=='3'}">
						| <a href="javascript:toCancelForm('<%=id%>')">ȡ��</a>
					</c:if>
				</c:if>
			</display:column>
		</display:table>
	</body>
</html>

