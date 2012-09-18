<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function edit(id){
		window.location.href = '${ctx}/oneStopQuick_inputEqu.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("删除将不能恢复，请确认是否要删除该设备，按‘确定’删除？")){
			window.location.href = '${ctx}/oneStopQuick_deleteEqu.jspx?id='+id;
    	}
	}
</script>
<body style="overflow-x:hidden;">
<template:titile value="设备列表"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle" requestURI="${ctx }/oneStopQuick_listEqu.jspx">
	<display:column title="站址编号" maxLength="15" sortable="true">
		<c:out value="${BASESTATIONS[row.parentId]}"  />
	</display:column>
	<display:column title="设备类型"  maxLength="15" sortable="true">
		<c:out value="${EQUIPMENTSTYPE[row.equTypeId]}" />
	</display:column>
	<display:column property="equName" title="设备名称"  maxLength="15" sortable="true"/>
	<display:column property="equProducter" title="设备厂商"  maxLength="15" sortable="true"/>
	<display:column property="equModel" title="设备型号"  maxLength="15" sortable="true"/>
	<display:column property="equDeploy" title="配置"  maxLength="15" sortable="true"/>
	<display:column media="html" title="操作" paramId="tid">
		<a href="javascript:edit('${row.id}')">修改</a>
		<a href="javascript:del('${row.id}')">删除</a>
	</display:column>
</display:table>
<br/>
<div align="right"><input type="button" class="button" onClick="window.location.href ='/bspweb/resource/onestop/oneStopQuick_inputEqu.jspx?flag=save'" value="添加设备"> </div>
</body>