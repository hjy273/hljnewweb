<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>�����ʾ�</title>
		<script type="text/javascript">
			toPerfectIssue=function(issueId){
            	window.location.href = "${ctx}/questAction.do?method=editQuestForm&issueId="+issueId;
			}
			toDeleteIssue=function(issueId){
				if(confirm("ȷ��ɾ�����ʾ���")){
            		window.location.href = "${ctx}/questAction.do?method=deleteQuest&issueId="+issueId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="�����ʾ�"/>
		<display:table name="sessionScope.list" id="issue" pagesize="18">
			<display:column property="id" title="���" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="name" title="�ʾ�����" headerClass="subject"  sortable="true"/>
			<display:column property="type" title="�ʾ�����" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toPerfectIssue('<bean:write name="issue" property="id"/>')">����</a> | 
				<a href="javascript:toDeleteIssue('<bean:write name="issue" property="id"/>')">ɾ��</a>
			</display:column>
		</display:table>
	</body>
</html>
