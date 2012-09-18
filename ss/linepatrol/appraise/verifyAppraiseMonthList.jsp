<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>考核结果确认</title>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
</head>
<script type="text/javascript">
	function verify(id) {
		window.location.href="${ctx}/appraiseMonthAction.do?method=viewAppraiseMonth&flag=verify&resultId="+id;
	}
</script>
<body>
	<template:titile value="考核结果确认" />
		<display:table name="sessionScope.appraiseResults"
			id="appraiseResults" pagesize="18" export="false" defaultsort="1"
			defaultorder="descending" sort="list">
			<display:column property="appraiseTime" title="考核月份" format="{0,date,yyyy-MM}"
				sortable="true" />
			<display:column media="html" title="代维公司" sortable="true">
				<c:out value="${contractor[appraiseResults.contractorId]}"></c:out>
			</display:column>
			<display:column property="contractNO" title="标底包" maxLength="9"
				sortable="true" />
				<display:column property="result" sortable="true" title="考核结果" />
			<display:column property="appraiseDate" sortable="true"
				format="{0,date,yyyy-MM-dd}" title="考核时间" />
			<display:column property="appraiser" title="考核人" sortable="true" ></display:column>
			<display:column media="html" title="操作">
				<a href="javascript:verify('${appraiseResults.id}');">确认</a>
			</display:column>
		</display:table>
</body>
</html>
