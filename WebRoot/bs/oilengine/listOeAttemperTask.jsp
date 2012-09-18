<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="text/javascript">
	function view(id){
		window.location.href = '${ctx}/oeAttemperTaskAction_view.jspx?flag=view&id='+id;
	}
	function edit(id){
		window.location.href = '${ctx}/oeAttemperTaskAction_view.jspx?flag=edit&id='+id;
	}
	function del(id){
	if(confirm("确定要删除该执行计划吗？")){
		window.location.href = '${ctx}/oeAttemperTaskAction_del.jspx?id='+id;
		}
	}
</script>
</head>
<body>
	<template:titile value="基站断电告警列表"/>
	<display:table name="session.RESULTLIST" pagesize="18" export="fasle" id="row">
		<display:column property="blackoutStation" title="中断基站"></display:column>
		<display:column property="blackoutTime" title="中断时间"></display:column>
		<display:column title="操作">
			<a href="javascript:view('${row.id}')">查看</a>|
			<a href="javascript:edit('${row.id}')">修改</a>|
			<a href="javascript:del('${row.id}')">删除</a>
		</display:column>
	</display:table>
</body>
