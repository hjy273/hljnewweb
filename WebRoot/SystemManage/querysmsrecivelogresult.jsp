<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="查询接收短信日志结果"/>
<display:table name="sessionScope.queryresult"  pagesize="18" >
	<display:column property="receivetime" title="接收时间" sortable="true" />
	
	<display:column property="session_key" title="会话ID"  sortable="true" />
	<display:column property="deviceid" title="设备编号"   sortable="true"  />
	<display:column property="patrolname" title="巡检组"  sortable="true"  />
	<display:column property="content" title="短信内容" maxLength="80" sortable="true"  />
	
</display:table>
<html:link action="/PatrolOpAction.do?method=exportSmsReceiveLog">导出为Excel文件</html:link>
