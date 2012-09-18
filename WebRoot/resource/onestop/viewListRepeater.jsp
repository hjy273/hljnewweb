<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/oneStopQuick_inputRepeater.jspx?flag=edit&id='+id;
	}
</script>
<template:titile value="直放站列表"/>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18" export="fasle">
	<display:column property="repeaterType" title="直放站号" maxLength="15" sortable="true" />
	<display:column property="city" title="所属城市" maxLength="15" sortable="true" />
	<display:column property="installPlace" title="安装位置" maxLength="15" sortable="true" />
	<display:column property="longitude" title="经度" maxLength="15" sortable="true" />
	<display:column property="latitude" title="纬度" maxLength="15" sortable="true" />
	<display:column property="coverageArea" title="覆盖范围" maxLength="15" sortable="true" />
	<display:column property="coverageAreaType" title="覆盖区域类型" maxLength="15" sortable="true" />
	<display:column property="createTime" title="建站时间" format="{0,date,yyyy年MM月dd日}" maxLength="15"
		sortable="true" />
	<display:column title="维护单位" maxLength="15" sortable="true">
		<c:out value="${sessionScope.maintenances[row.maintenanceId]}"></c:out>
	</display:column>
	<display:column media="html" title="操作" paramId="tid">
		<a href="javascript:view('${row.id}')">查看</a>
	</display:column>
</display:table>
<br />
