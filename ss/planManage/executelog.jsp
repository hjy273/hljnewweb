<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<body>
<template:titile value="�ƻ��ƶ����"/>
<div id="display">
<display:table name="requestScope.ExecuteLogList"   id="currentRowObject"  pagesize="18">
	<display:column property="userid" title="�ƶ���" />
	<display:column property="createdate" title="ִ��ʱ��"/>
	<display:column property="type" title="����"/>
	<display:column property="result" href="${ctx}/PlanAction.do?method=queryPlan"  paramId="createdate" paramProperty="createdate" title="ִ�н��" maxLength="30"/>
	<display:column property="enddate" title="����ʱ��"/>
</display:table>
</div>
</body>
 