<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="查询接收短信日志结果"/>
<display:table name="requestScope.queryresultpage"  pagesize="18" requestURI="/smsLogAction.do?method=querySMS_SendLog" sort="list"  id="diaplaytable" defaultsort="1">
	<display:column property="arrivetime" title="接收时间" sortable="true" defaultorder="descending" sortName="arrivetime"/>
	<display:column property="sim" title="发送号码"  sortable="true"  />
	<display:column property="region" title="所属区域"   sortable="true"  />
	<display:column property="content" title="短信内容" maxLength="20" sortable="true"  />
	<display:column property="handlestate" title="状态"  sortable="true"  />
</display:table>
<html:link action="/PatrolOpAction.do?method=exportSmsReceiveLog">导出为Excel文件</html:link>
