<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<head>
	<script type="text/javascript">
		function getEqu(id){
		window.location.href='${ctx}/baseStationAction_view.jspx?flag=view&id='+id;
		}
	function exportForm(){
		window.location.href="${ctx}/equipmentAction_exportNewEqu.jspx?";
		}
	</script>
</head>
<body>
	<template:titile value="�����豸�б�"/>
	<display:table name="sessionScope.NEWEQUIPMENTS" id="new" pagesize="18" export="fasle">
		<display:column title="�豸����">
			${EQUIPMENTSTYPE[new['equ_dict_id']]}
		</display:column>
		<display:column title="�ʲ�����">
			${new['equ_name'] }
		</display:column>
		<display:column title="�豸����">
			${new['equ_producter'] }
		</display:column>
		<display:column title="����">
			${new['equ_deploy'] }
		</display:column>
		<display:column title="���ڻ�վ">
			<a href="javascript:getEqu('${new['parent_id']}')">${new['station_name']}</a>
		</display:column>
	</display:table>
	<div align="left">
<a href="javascript:exportForm();">����Excel�ļ�</a>
</div>
	<div align="center"><input type="button" class="button" onclick="history.back()" value="����" ></div>
</body>