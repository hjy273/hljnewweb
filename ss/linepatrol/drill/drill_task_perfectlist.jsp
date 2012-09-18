<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>������ѯ�б�</title>
		<script type="text/javascript">
			toPerfectDrillTask=function(taskId){
            	window.location.href = "${ctx}/drillTaskAction.do?method=perfectDrillTaskForm&taskId="+taskId;
			}
			toDeleteTempTask=function(taskId){
				if(confirm("��ȷ��Ҫɾ������ʱ����������")){
            		window.location.href = "${ctx}/drillTaskAction.do?method=deleteTempTask&taskId="+taskId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="������ѯ�б�"/>
		<display:table name="sessionScope.list" id="drill" pagesize="18">
			<display:column property="name" title="��������" headerClass="subject"  sortable="true"/>
			<display:column property="drill_level" title="��������" headerClass="subject"  sortable="true"/>
			<display:column property="task_begin_time" title="���鿪ʼʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="task_end_time" title="�������ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="locale" title="�����ص�" headerClass="subject"  sortable="true"/>
			<display:column property="remark" title="��ע" headerClass="subject"  sortable="true" maxLength="15"/>
			<display:column property="task_createtime" title="����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toPerfectDrillTask('<bean:write name="drill" property="id"/>')">��������</a> | 
				<a href="javascript:toDeleteTempTask('<bean:write name="drill" property="id"/>')">ɾ��</a>
			</display:column>
		</display:table>
	</body>
</html>

