<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/stationAction_view.jspx?flag=view&id='+id;
	}
	function edit(id){
		window.location.href = '${ctx}/stationAction_view.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ���û�վ������ȷ����ɾ����")){
			window.location.href = '${ctx}/stationAction_delete.jspx?id='+id;
    	}
	}
</script>
<template:titile value="��ѯ��վ��Ϣ���"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle">
	<display:column property="code" title="��վ���"  maxLength="15"/>
	<display:column property="stationname" title="��վ����"  maxLength="15"/>
	<display:column property="stationtype" title="��վ����"  maxLength="15"/>
	<display:column property="stationlevel" title="��վ����"  maxLength="15"/>
	<display:column property="regionname" title="����"  maxLength="15"/>
	<display:column media="html" title="����" paramId="tid">
		<a href="javascript:view('${row.tid}')">�鿴</a>

		<a href="javascript:edit('${row.tid}')">�޸�</a>
		<a href="javascript:del('${row.tid}')">ɾ��</a>
	</display:column>
</display:table>