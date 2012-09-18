<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<head>
	<script type="text/javascript">
		function getEqu(id){
		window.location.href='${ctx}/baseStationAction_view.jspx?flag=view&id='+id;
		}
	function exportForm(){
		window.location.href="${ctx}/equipmentAction_exportChangeEqu.jspx?";
		}
	</script>
</head>
<body>
	<template:titile value="����豸�б�"/>
	<display:table name="sessionScope.CHANGEEQUIPMENTS" id="change" pagesize="18" export="false">
		<display:column title="�豸����">
			${EQUIPMENTSTYPE[change['equ_dict_id']]}
		</display:column>
		<display:column title="�ʲ�����">
			${change['equ_name'] }
		</display:column>
		<display:column title="�豸����">
		${change['equ_producter'] }
		</display:column>
		<display:column title="����">
			${change['equ_deploy'] }
		</display:column>
		<display:column title="�����ص�">
			<a href="javascript:getEqu('${change['oldbsid'] }')">
			${change['oldBaseStation'] }</a>
		</display:column>
		<display:column title="����ص�">
			<a href="javascript:getEqu('${change['parent_id'] }')">
			${change['newBaseStation'] }</a>
		</display:column>
	</display:table>
	<div align="left">
<a href="javascript:exportForm();">����Excel�ļ�</a>
</div>
	<div align="center"><input type="button" class="button" onclick="history.back()" value="����" ></div>
</body>