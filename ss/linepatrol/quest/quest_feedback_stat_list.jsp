<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="/WebApp/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>��������б�</title>
		<script type="text/javascript">
			toQuestFeedbackStat=function(issueId){
            	window.location.href = "/WebApp/questAction.do?method=questFeedbackStat&issueId="+issueId;
			}
		</script>
	</head>
	<body>
		<template:titile value="��������б�"/>
		<display:table name="sessionScope.list" id="issue" pagesize="18">
			<display:column property="id" title="���" headerClass="subject" sortable="true"/>
			<display:column property="name" title="�ʾ�����" headerClass="subject"  sortable="true"/>
			<display:column property="type" title="�ʾ�����" headerClass="subject"  sortable="true"/>
			<display:column property="create_date" title="����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toQuestFeedbackStat('<bean:write name="issue" property="id"/>')">�鿴</a>
			</display:column>
		</display:table>
	</body>
</html>
