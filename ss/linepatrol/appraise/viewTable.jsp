<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<%@page import="com.cabletech.linepatrol.cut.dao.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel='stylesheet' type='text/css'
	href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript'
	src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>�鿴���˱�</title>
		<script type="text/javascript">
			function saveTable(){
    			if(confirm("ȷ��Ҫ��Ӹ�ģ�壿")){
					window.location.href="${ctx}/appraiseTableMonthAction.do?method=saveTable";
				}
			}
			function back(){
				window.location.href="${ctx}/appraiseTableMonthAction.do?method=appraiseTableList";
			}
			function cancel(){
				window.location.href="${ctx}/linepatrol/appraise/importMonthExcel.jsp";
			}
		</script>
</head>

		<body>
		<template:titile value="�鿴���˱�"/>
			${html}
			<c:if test="${flag eq 'import'}">
			<center><input type="button" value="ȷ�ϵ���" class="button" onclick="javascript:saveTable();"> <input type="button" value="ȡ������" class="button" onclick="javascript:cancel();"></center>
			</c:if>
			<c:if test="${flag eq 'view'}">
			<c:set value="100" var="allWeight"></c:set>
			<center><input type="button" value="����" class="button" onclick="javascript:back();"></center>
			</c:if>
		</body>
</html>
