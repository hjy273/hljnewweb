<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>�ʾ����͹���</title>
		<script type="text/javascript">
			toAddType=function(){
            	window.location.href = "${ctx}/questAction.do?method=addTypeForm";
			}
			toEditType=function(typeId){
            	window.location.href = "${ctx}/questAction.do?method=editTypeForm&typeId="+typeId;
			}
			toDeleteType=function(typeId){
				if(confirm("ȷ��ɾ�����ʾ�������")){
            		window.location.href = "${ctx}/questAction.do?method=deleteType&typeId="+typeId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="�ʾ����͹���"/>
		<display:table name="sessionScope.list" id="type" pagesize="18">
			<display:column property="row_num" title="���" headerClass="subject" sortable="true"/>
			<display:column property="type_name" title="�ʾ�����" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toEditType('<bean:write name="type" property="id"/>')">�༭</a> | 
				<a href="javascript:toDeleteType('<bean:write name="type" property="id"/>')">ɾ��</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddType()">���</html:button>
		</div>
	</body>
</html>
