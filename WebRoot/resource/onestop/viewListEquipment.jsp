<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/oneStopQuick_inputEqu.jspx?flag=edit&id='+id;
	}
</script>
<template:titile value="设备列表"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle">
	<display:column title="站址编号" maxLength="15" sortable="true">
		<c:out value="${sessionScope.BASESTATIONS[row.parentId]}"  />
	</display:column>
	<display:column title="设备名称"  maxLength="15" sortable="true">
		<c:out value="${EQUIPMENTTYPE[row.equTypeId]}" />
	</display:column>
	<display:column property="equProducter" title="设备厂商"  maxLength="15" sortable="true"/>
	<display:column property="equModel" title="设备型号"  maxLength="15" sortable="true"/>
	<display:column property="equDeploy" title="配置"  maxLength="15" sortable="true"/>
	<display:column media="html" title="操作" paramId="tid">
		<a href="javascript:view('${row.id}')">查看</a>
	</display:column>
</display:table>
<br/>
