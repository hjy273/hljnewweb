<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>调查问卷</title>
		<script type="text/javascript">
			toPerfectIssue=function(issueId){
            	window.location.href = "${ctx}/questAction.do?method=editQuestForm&issueId="+issueId;
			}
			toDeleteIssue=function(issueId){
				if(confirm("确认删除该问卷吗？")){
            		window.location.href = "${ctx}/questAction.do?method=deleteQuest&issueId="+issueId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="调查问卷"/>
		<display:table name="sessionScope.list" id="issue" pagesize="18">
			<display:column property="id" title="序号" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="name" title="问卷名称" headerClass="subject"  sortable="true"/>
			<display:column property="type" title="问卷类型" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toPerfectIssue('<bean:write name="issue" property="id"/>')">完善</a> | 
				<a href="javascript:toDeleteIssue('<bean:write name="issue" property="id"/>')">删除</a>
			</display:column>
		</display:table>
	</body>
</html>
