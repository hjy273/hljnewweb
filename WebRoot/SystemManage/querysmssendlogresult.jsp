<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="查询发送短信日志结果"/>
<display:table name="sessionScope.queryresult"   pagesize="18" >
	<display:column property="sendtime" title="发送时间" sortable="true" />
	<display:column property="username" title="发送者"  sortable="true" />
	<display:column property="simid" title="接收号码"  sortable="true"  />
	<display:column property="type" title="短信类型"  sortable="true"  />
	<display:column property="content" title="短信内容"  sortable="true"  />
	<display:column property="handlestate" title="状态"  sortable="true"  />
</display:table>
<html:link action="/PatrolOpAction.do?method=exportSmsSendLog">导出为Excel文件</html:link>
