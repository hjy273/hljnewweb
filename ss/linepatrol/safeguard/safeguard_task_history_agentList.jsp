<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>���ϲ�ѯ�б�</title>
		<script type="text/javascript">
			toViewSafeguard=function(taskId,planId,summaryId,conId){
            	window.location.href = "${ctx}/safeguardQueryStatAction.do?method=viewSafeguard&taskId="+taskId+"&planId="+planId+"&summaryId="+summaryId+"&conId="+conId;
			}
		//ȡ������
		toCancelForm=function(safeguardTaskId){
			var url;
			if(confirm("ȷ��Ҫȡ���ñ������������ȡ�����������еķַ������̡�")){
				url="${ctx}/safeguardTaskAction.do?method=cancelSafeguardTaskForm";
				var queryString="safeguard_task_id="+safeguardTaskId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<body>
		<template:titile value="ȫ���Ѱ칤�� (<font color='red'>��${num }���Ѱ�</font>)"/>
		<!-- <div style="text-align:center;">
			<iframe
				src="${ctx}/safeguardTaskAction.do?method=viewSafeGuardProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div> -->
		<display:table name="sessionScope.list" id="safeguard" pagesize="18">
			<logic:notEmpty name="safeguard">
				<bean:define id="sendUserId" name="safeguard" property="sender" />
				<bean:define id="id" name="safeguard" property="task_id"></bean:define>
				<bean:define id="applyState" name="safeguard" property="safeguard_state"></bean:define>
			</logic:notEmpty>
			<display:column property="task_name" title="��������" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="contractorname" title="��ά��λ" headerClass="subject"  sortable="true"/>
			<display:column property="safeguard_level" title="���ϼ���" headerClass="subject"  sortable="true"/>
			<display:column property="region" title="���ϵص�" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="username" title="���񴴽���" headerClass="subject"  sortable="true"/>
			<display:column property="task_createtime" title="����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="transact_state" title="״̬" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toViewSafeguard('<bean:write name="safeguard" property="task_id"/>','<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="summary_id"/>','<bean:write name="safeguard" property="con_id"/>')">�鿴</a>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<c:if test="${applyState=='1'}">
						| <a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>')">ȡ��</a>
					</c:if>
					<c:if test="${applyState=='2'}">
						| <a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>')">ȡ��</a>
					</c:if>
					<c:if test="${applyState=='3'}">
						| <a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>">ȡ��</a>
					</c:if>
				</c:if>
			</display:column>
		</display:table> 
	</body>
</html>

