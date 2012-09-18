<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<body>
<template:titile value="计划制定结果"/>
<div id="display">
<display:table name="requestScope.ExecuteLogList"   id="currentRowObject"  pagesize="18">
	<display:column property="userid" title="制定人" />
	<display:column property="createdate" title="执行时间"/>
	<display:column property="type" title="类型"/>
	<display:column property="result" href="${ctx}/PlanAction.do?method=queryPlan"  paramId="createdate" paramProperty="createdate" title="执行结果" maxLength="30"/>
	<display:column property="enddate" title="结束时间"/>
</display:table>
</div>
</body>
 