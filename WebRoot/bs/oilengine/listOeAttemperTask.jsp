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
	if(confirm("ȷ��Ҫɾ����ִ�мƻ���")){
		window.location.href = '${ctx}/oeAttemperTaskAction_del.jspx?id='+id;
		}
	}
</script>
</head>
<body>
	<template:titile value="��վ�ϵ�澯�б�"/>
	<display:table name="session.RESULTLIST" pagesize="18" export="fasle" id="row">
		<display:column property="blackoutStation" title="�жϻ�վ"></display:column>
		<display:column property="blackoutTime" title="�ж�ʱ��"></display:column>
		<display:column title="����">
			<a href="javascript:view('${row.id}')">�鿴</a>|
			<a href="javascript:edit('${row.id}')">�޸�</a>|
			<a href="javascript:del('${row.id}')">ɾ��</a>
		</display:column>
	</display:table>
</body>
