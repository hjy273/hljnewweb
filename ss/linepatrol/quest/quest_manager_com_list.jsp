<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>�����������</title>
		<script type="text/javascript">
			toAddCom=function(){
            	window.location.href = "${ctx}/questAction.do?method=addComForm";
			}
			toEditCom=function(comId){
            	window.location.href = "${ctx}/questAction.do?method=editComForm&comId="+comId;
			}
			toDeleteCom=function(comId){
				if(confirm("ȷ��ɾ���ò���������")){
            		window.location.href = "${ctx}/questAction.do?method=deleteCom&comId="+comId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="�����������"/>
		<display:table name="sessionScope.list" id="com" pagesize="18">
			<display:column property="row_num" title="���" headerClass="subject" sortable="true"/>
			<display:column property="com_name" title="��������" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toEditCom('<bean:write name="com" property="id"/>')">�༭</a> | 
				<a href="javascript:toDeleteCom('<bean:write name="com" property="id"/>')">ɾ��</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddCom()">���</html:button>
		</div>
	</body>
</html>
