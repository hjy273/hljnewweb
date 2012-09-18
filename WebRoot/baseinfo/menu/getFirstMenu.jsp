<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/MenuManageAction.do?method=viewFirstMenu&flag=view&id='+id;
	}
	function edit(id){
		window.location.href = '${ctx}/MenuManageAction.do?method=viewFirstMenu&flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("ȷ��ɾ���ò˵���")){
			window.location.href = '${ctx}/MenuManageAction.do?method=deleteFirstMenu&id='+id;
    	}
	}
</script>
<template:titile value="��ѯһ���˵����"/>
<display:table name="sessionScope.list" id="row" pagesize="18" export="true">
	<display:column property="id" title="�˵�id"/>
	<display:column property="lablename" title="�˵�����"/>
	<display:column property="businessTypeName" title="ҵ������"/>
	<display:column property="imgurl" title="ͼ��·��"/>
	<display:column property="showno" title="���"/>
	<display:column media="html" title="�鿴">
		<a href="javascript:view('${row.id}')">�鿴</a>
	</display:column>
	<display:column media="html" title="�޸�">
		<a href="javascript:edit('${row.id}')">�޸�</a>
	</display:column>
	<display:column media="html" title="ɾ��">
		<a href="javascript:del('${row.id}')">ɾ��</a>
	</display:column>
</display:table>