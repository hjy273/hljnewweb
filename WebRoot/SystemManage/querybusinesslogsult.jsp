<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="查询系统操作日志信息结果"/>
<display:table name="sessionScope.queryresult"   pagesize="18" >
	<display:column property="logdate" title="登陆时间" sortable="true" />
	<display:column property="userid" title="用户ID"  sortable="true" />
	<display:column property="username" title="用户姓名"  sortable="true" />
	<display:column property="ip" title="登陆IP"  sortable="true"  />
	<display:column property="module" title="操作模块"  sortable="true"  />
	<display:column property="msg" title="额外信息"  sortable="true"  maxLength="25"/>
</display:table>
<html:link action="/PatrolOpAction.do?method=exportSysLog">导出为Excel文件</html:link>
