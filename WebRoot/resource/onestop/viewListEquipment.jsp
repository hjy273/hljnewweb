<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/oneStopQuick_inputEqu.jspx?flag=edit&id='+id;
	}
</script>
<template:titile value="�豸�б�"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle">
	<display:column title="վַ���" maxLength="15" sortable="true">
		<c:out value="${sessionScope.BASESTATIONS[row.parentId]}"  />
	</display:column>
	<display:column title="�豸����"  maxLength="15" sortable="true">
		<c:out value="${EQUIPMENTTYPE[row.equTypeId]}" />
	</display:column>
	<display:column property="equProducter" title="�豸����"  maxLength="15" sortable="true"/>
	<display:column property="equModel" title="�豸�ͺ�"  maxLength="15" sortable="true"/>
	<display:column property="equDeploy" title="����"  maxLength="15" sortable="true"/>
	<display:column media="html" title="����" paramId="tid">
		<a href="javascript:view('${row.id}')">�鿴</a>
	</display:column>
</display:table>
<br/>
