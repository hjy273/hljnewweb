<%@include file="/common/header.jsp"%>

<html>
	<head>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
			.subject{text-align:center;}
		</style>
		<script type="text/javascript">
			function sign(tid) {
				var url = "PollingTaskAction.do?method=signATask&tid="+tid;
				window.location.href=url;
			}
			
			function goBack() {
				var url = "MobileTaskAction.do?method=getTaskForCon";
				window.location.href = url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="����Ѳ���豸��Ϣһ����"/>
		<display:table name="sessionScope.oneTaskList" id="currentRowObject" pagesize="18">
			<display:column property="numerical" sortable="true" class="subject" title="���к�" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="conname" sortable="true" title="��ά��˾" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="exename" sortable="true" title="ִ����" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="exetime" sortable="true" title="ִ������" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="ename" sortable="true" title="�豸����" headerClass="subject" maxLength="20" class="subject" />
		</display:table>
		
		<div style="text-align: center; margin-top: 10px;">
			<input type="button" value="ǩ�ո�����" class="button2" onclick="sign('<bean:write name="tid" />')">
			<input type="button" value="����" class="button" onclick="goBack()">	
		</div>
	</body>
</html>