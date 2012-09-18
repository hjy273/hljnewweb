<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="">
	function edit(id){
		window.location.href = '${ctx}/oneStopQuick_inputRepeater.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ����ֱ��վ������ȷ����ɾ����")){
			window.location.href = '${ctx}/oneStopQuick_deleteRepeater.jspx?id='+id;
    	}
	}
</script>
<body style="overflow-x: hidden;">
	<template:titile value="ֱ��վ�б�" />
	<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
		export="fasle" requestURI="${ctx }/oneStopQuick_listRepeater.jspx">
		<display:column property="repeaterType" title="ֱ��վ��" maxLength="15"
			sortable="true" />
		<display:column property="city" title="��������" maxLength="15"
			sortable="true" />
		<display:column property="installPlace" title="��װλ��" maxLength="15"
			sortable="true" />
		<display:column title="���Ƿ�Χ" maxLength="15" sortable="true">
			<apptag:dynLabel dicType="overlay_area" objType="dic"
				id="${row.coverageArea}"></apptag:dynLabel>
		</display:column>
		<display:column title="������������" maxLength="15" sortable="true">
			<apptag:dynLabel dicType="overlay_area_type" objType="dic"
				id="${row.coverageAreaType}"></apptag:dynLabel>
		</display:column>
		<display:column property="attachBsc" title="����BSC" maxLength="15"
			sortable="true" />
		<display:column title="��Դ��վ" maxLength="15" sortable="true">
			<c:out value="${BASESTATIONS[row.msBsId]}"></c:out>
		</display:column>
		<display:column property="createTime" title="��վʱ��"
			format="{0,date,yyyy��MM��dd��}" maxLength="15" sortable="true" />
		<display:column title="ά����λ" maxLength="15" sortable="true">
			<c:out value="${sessionScope.maintenances[row.maintenanceId]}"></c:out>
		</display:column>
		<display:column media="html" title="����" paramId="tid">
			<a href="javascript:edit('${row.id}')">�޸�</a>
			<a href="javascript:del('${row.id}')">ɾ��</a>
		</display:column>
	</display:table>
	<br />
	<div align="right">
		<input type="button" class="button"
			onClick="window.location.href ='/bspweb/resource/onestop/oneStopQuick_inputRepeater.jspx?flag=save'"
			value="���ֱ��վ">
	</div>
</body>