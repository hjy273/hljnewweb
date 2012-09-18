<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>ָ��������</title>
		<script type="text/javascript">
			toAddClass=function(){
            	window.location.href = "${ctx}/questAction.do?method=addClassForm";
			}
			toEditClass=function(classId){
            	window.location.href = "${ctx}/questAction.do?method=editClassForm&classId="+classId;
			}
			toDeleteClass=function(classId){
				if(confirm("ȷ��ɾ����ָ�������")){
            		window.location.href = "${ctx}/questAction.do?method=deleteClass&classId="+classId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="ָ��������"/>
		<display:table name="sessionScope.list" id="class" pagesize="18">
			<display:column property="row_num" title="���" headerClass="subject" sortable="true"/>
			<display:column property="type_name" title="�ʾ�����" headerClass="subject"  sortable="true"/>
			<display:column property="class_name" title="ָ�����" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="����" >
				<a href="javascript:toEditClass('<bean:write name="class" property="id"/>')">�༭</a> | 
				<a href="javascript:toDeleteClass('<bean:write name="class" property="id"/>')">ɾ��</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddClass()">���</html:button>
		</div>
	</body>
</html>
