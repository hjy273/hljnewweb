<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="/WebApp/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>调查汇总列表</title>
		<script type="text/javascript">
			toQuestFeedbackStat=function(issueId){
            	window.location.href = "/WebApp/questAction.do?method=questFeedbackStat&issueId="+issueId;
			}
		</script>
	</head>
	<body>
		<template:titile value="调查汇总列表"/>
		<display:table name="sessionScope.list" id="issue" pagesize="18">
			<display:column property="id" title="序号" headerClass="subject" sortable="true"/>
			<display:column property="name" title="问卷名称" headerClass="subject"  sortable="true"/>
			<display:column property="type" title="问卷类型" headerClass="subject"  sortable="true"/>
			<display:column property="create_date" title="创建时间" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toQuestFeedbackStat('<bean:write name="issue" property="id"/>')">查看</a>
			</display:column>
		</display:table>
	</body>
</html>
