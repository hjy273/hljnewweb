<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function edit(id){
		window.location.href = '${ctx}/oneStopQuick_inputEqu.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ�����豸������ȷ����ɾ����")){
			window.location.href = '${ctx}/oneStopQuick_deleteEqu.jspx?id='+id;
    	}
	}
</script>
<body style="overflow-x:hidden;">
<template:titile value="�豸�б�"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle" requestURI="${ctx }/oneStopQuick_listEqu.jspx">
	<display:column title="վַ���" maxLength="15" sortable="true">
		<c:out value="${BASESTATIONS[row.parentId]}"  />
	</display:column>
	<display:column title="�豸����"  maxLength="15" sortable="true">
		<c:out value="${EQUIPMENTSTYPE[row.equTypeId]}" />
	</display:column>
	<display:column property="equName" title="�豸����"  maxLength="15" sortable="true"/>
	<display:column property="equProducter" title="�豸����"  maxLength="15" sortable="true"/>
	<display:column property="equModel" title="�豸�ͺ�"  maxLength="15" sortable="true"/>
	<display:column property="equDeploy" title="����"  maxLength="15" sortable="true"/>
	<display:column media="html" title="����" paramId="tid">
		<a href="javascript:edit('${row.id}')">�޸�</a>
		<a href="javascript:del('${row.id}')">ɾ��</a>
	</display:column>
</display:table>
<br/>
<div align="right"><input type="button" class="button" onClick="window.location.href ='/bspweb/resource/onestop/oneStopQuick_inputEqu.jspx?flag=save'" value="����豸"> </div>
</body>