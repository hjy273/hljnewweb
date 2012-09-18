<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
		<link rel='stylesheet' type='text/css'
			href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript'
			src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="text/javascript">
	function redeploy(id){
		window.location.href = '${ctx}/oeAttemperTaskAction_viewRedeploy.jspx?radius=0&id='+id;
	}
</script>
<template:titile value="断电基站列表"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle">
	<display:column title="站址编号" property="stationCode"  maxLength="30" sortable="true"/>
	<display:column title="基站名称" property="stationName"  maxLength="30" sortable="true"/>
	<display:column title="基站级别">
	${STATIONLEVELS[row.bsLevel]}
	</display:column>
	<display:column property="blackoutTime" title="断电时间"  maxLength="30" sortable="true"/>
	<display:column title="断电原因" property="blackoutReason"  maxLength="30" sortable="true"/>
	<display:column media="html" title="操作" paramId="tid">
	<a href="javascript:redeploy('${row.id}')">处理</a>
	</display:column>
</display:table>
