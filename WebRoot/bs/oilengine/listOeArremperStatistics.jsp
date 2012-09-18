<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<link rel='stylesheet' type='text/css'	href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript'	src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<head>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="text/javascript">
	function view(id){
		window.location.href = '${ctx}/oeAttemperTaskAction_viewOeAttemperTask.jspx?id='+id;
	}
	function exportForm() {
		var actionUrl = "${ctx}/oeAttemperTaskAction_exportOeAttemperTaskForm.jspx";
		//window.open(actionUrl);
		win = new Ext.Window( {
			layout : 'fit',
			width : 650,
			height : 400,
			resizable : true,
			closeAction : 'close',
			modal : true,
			autoScroll : true,
			autoLoad : {
				url : actionUrl,
				scripts : true
			},
			plain : true,
			title : "ѡ��Ҫ�����Ķϵ��վ��Ϣ����"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
</head>
<body>
	<template:titile value="��վ�ϵ�ͳ���б�"/>
	<display:table name="session.OEATTEMPERTASKS" pagesize="18" export="fasle" id="row">
		<display:column property="stationName" title="�ϵ��վ"></display:column>
		<display:column property="blackoutTime" title="�ϵ�ʱ��"></display:column>
		<display:column property="blackoutReason" title="�ϵ�ԭ��"></display:column>
		<display:column property="oilEngineCode" title="�ͻ����"></display:column>
		<display:column property="shiftTime" title="����ʱ��"></display:column>
		<display:column title="����">
			<a href="javascript:view('${row.id}')">�鿴</a>
		</display:column>
	</display:table>
	<div align="left">
		<a href="javascript:exportForm();">����Excel�ļ�</a>
	</div>
</body>
