<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function edit(id){
		window.location.href = '${ctx}/oneStopQuick_inputCell.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ����С��������ȷ����ɾ����")){
			window.location.href = '${ctx}/oneStopQuick_deleteCell.jspx?id='+id;
    	}
	}
</script>
<body style="overflow-x:hidden;">
<template:titile value="С���б�"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle" requestURI="${ctx }/oneStopQuick_listCell.jspx">
	<display:column title="վַ���" maxLength="15" sortable="true">
		<c:out value="${sessionScope.BASESTATIONS[row.parentId]}"  />
	</display:column>
	<display:column property="cellCode" title="С����"  maxLength="15" sortable="true"/>
	<display:column property="chineseName" title="��������"  maxLength="15" sortable="true"/>
	<display:column title="�Ƿ��Ƶ"  maxLength="15" sortable="true">
		<c:if test="${row.isFrequencyHopping == 'y'}">��</c:if>
		<c:if test="${row.isFrequencyHopping != 'y'}">��</c:if>
	</display:column>
	<display:column title="�Ƿ��´�ֱ��վ"  maxLength="15" sortable="true">
		<c:if test="${row.isOwnRepeater == 'y'}">��</c:if>
		<c:if test="${row.isOwnRepeater != 'y'}">��</c:if>
	</display:column>
	<display:column property="carrierFrequencyNum" title="��Ƶ��"  maxLength="15" sortable="true"/>
	<display:column property="anTime" title="����ʱ��" format="{0,date,yyyy��MM��dd��}" maxLength="15" sortable="true"/>
	<display:column media="html" title="����" paramId="tid">
		<a href="javascript:edit('${row.id}')">�޸�</a>
		<a href="javascript:del('${row.id}')">ɾ��</a>
	</display:column>
</display:table>
<br/>
<div align="right"><input type="button" class="button" onClick="window.location.href ='/bspweb/resource/onestop/oneStopQuick_inputCell.jspx?flag=save'" value="���С��"> </div>
</body>