<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>���ϲ�ѯ�б�</title>
		<script type="text/javascript">
			toPerfectSafeguardTask=function(taskId){
            	window.location.href = "${ctx}/safeguardTaskAction.do?method=perfectSafeguardTaskForm&taskId="+taskId;
			}
			toDeleteTempTask=function(taskId){
				if(confirm("��ȷ��Ҫɾ������ʱ����������")){
            		window.location.href = "${ctx}/safeguardTaskAction.do?method=deleteTempTask&taskId="+taskId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="���Ʊ�������"/>
		<display:table name="sessionScope.list" id="safeguard" pagesize="18">
			<display:column property="task_name" title="��������" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="safeguard_level" title="���ϼ���" headerClass="subject"  sortable="true"/>
			<display:column property="region" title="���ϵص�" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="task_createtime" title="����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toPerfectSafeguardTask('<bean:write name="safeguard" property="task_id"/>')">��������</a> | 
				<a href="javascript:toDeleteTempTask('<bean:write name="safeguard" property="task_id"/>')">ɾ��</a>
			</display:column>
		</display:table>
	</body>
</html>

