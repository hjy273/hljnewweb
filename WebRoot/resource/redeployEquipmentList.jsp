<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<head>
	<script type="text/javascript">
		function getEqu(id){
		window.location.href='${ctx}/baseStationAction_view.jspx?flag=view&id='+id;
	}
	function exportForm(){
		window.location.href="${ctx}/equipmentAction_exportRedeployEqu.jspx?";
		}
	</script>
</head>
<body>
	<template:titile value="调拨设备列表"/>
	<display:table name="sessionScope.REDEPLOYEQUIPMENTS" id="reploy" pagesize="18" export="false">
		<display:column title="设备类型">
			${EQUIPMENTSTYPE[reploy['equ_dict_id']]}
		</display:column>
		<display:column title="资产名称">
			${reploy['equ_name'] }
		</display:column>
		<display:column title="设备厂商">
			${reploy['equ_producter'] }
		</display:column>
		<display:column title="配置">
			${reploy['equ_deploy'] }
		</display:column>
		<display:column title="拨出地点编码">
			<a href="javascript:getEqu('${reploy['oldbsid'] }')">
			${reploy['oldBaseStation'] }</a>
		</display:column>
		<display:column title="拨入地点编码">
			<a href="javascript:getEqu('${reploy['parent_id'] }')">
			${reploy['newBaseStation'] }</a>
		</display:column>
	</display:table>
	<div align="left">
<a href="javascript:exportForm();">导出Excel文件</a>
</div>
	<div align="center"><input type="button" class="button" onclick="history.back()" value="返回" ></div>
</body>