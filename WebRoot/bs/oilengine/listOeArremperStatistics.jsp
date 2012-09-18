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
			title : "选择要导出的断电基站信息属性"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
</head>
<body>
	<template:titile value="基站断电统计列表"/>
	<display:table name="session.OEATTEMPERTASKS" pagesize="18" export="fasle" id="row">
		<display:column property="stationName" title="断电基站"></display:column>
		<display:column property="blackoutTime" title="断电时间"></display:column>
		<display:column property="blackoutReason" title="断电原因"></display:column>
		<display:column property="oilEngineCode" title="油机编号"></display:column>
		<display:column property="shiftTime" title="调动时间"></display:column>
		<display:column title="操作">
			<a href="javascript:view('${row.id}')">查看</a>
		</display:column>
	</display:table>
	<div align="left">
		<a href="javascript:exportForm();">导出Excel文件</a>
	</div>
</body>
