<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/oneStopQuick_inputRepeater.jspx?flag=edit&id='+id;
	}
</script>
<template:titile value="ֱ��վ�б�"/>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18" export="fasle">
	<display:column property="repeaterType" title="ֱ��վ��" maxLength="15" sortable="true" />
	<display:column property="city" title="��������" maxLength="15" sortable="true" />
	<display:column property="installPlace" title="��װλ��" maxLength="15" sortable="true" />
	<display:column property="longitude" title="����" maxLength="15" sortable="true" />
	<display:column property="latitude" title="γ��" maxLength="15" sortable="true" />
	<display:column property="coverageArea" title="���Ƿ�Χ" maxLength="15" sortable="true" />
	<display:column property="coverageAreaType" title="������������" maxLength="15" sortable="true" />
	<display:column property="createTime" title="��վʱ��" format="{0,date,yyyy��MM��dd��}" maxLength="15"
		sortable="true" />
	<display:column title="ά����λ" maxLength="15" sortable="true">
		<c:out value="${sessionScope.maintenances[row.maintenanceId]}"></c:out>
	</display:column>
	<display:column media="html" title="����" paramId="tid">
		<a href="javascript:view('${row.id}')">�鿴</a>
	</display:column>
</display:table>
<br />
