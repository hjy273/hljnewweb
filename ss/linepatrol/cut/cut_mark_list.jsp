<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>�������б�</title>
		<script type="text/javascript">
			//�鿴���ս���
			toViewApply=function(cutId){
            	window.location.href = "${ctx}/checkAndMarkAction.do?method=viewApply&cutId="+cutId;
			}
			//��������
			toCutRemarkForm=function(cutId){
            	window.location.href = "${ctx}/checkAndMarkAction.do?method=checkAndMarkFrom&cutId="+cutId;
			}
		</script>
	</head>
	<body>
		<template:titile value="�������б�"/>
		<display:table name="sessionScope.list" id="cut" pagesize="18">
			<display:column property="workorder_id" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_name" title="�������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="��Ӽ���" headerClass="subject"  sortable="true"/>
			<display:column property="contractorname" title="��ά��λ" headerClass="subject"  sortable="true"/>
			<display:column property="apply_date" title="����ʱ��" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="cut_state" title="���״̬" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toViewApply('<bean:write name="cut" property="id"/>')">�鿴</a> | 
				<a href="javascript:toCutRemarkForm('<bean:write name="cut" property="id"/>')">��������</a>
			</display:column>
		</display:table>
	</body>
</html>
