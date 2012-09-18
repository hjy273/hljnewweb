<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>调查问卷</title>
		<script type="text/javascript">
			toAddFeedbackIssue=function(issueId){
            	window.location.href = "${ctx}/questAction.do?method=addFeedbackIssueForm1&issueId="+issueId;
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
				<a href="javascript:toAddFeedbackIssue('<bean:write name="issue" property="id"/>')">填写</a>
			</display:column>
		</display:table>
	</body>
</html>
