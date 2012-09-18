<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>指标类别管理</title>
		<script type="text/javascript">
			toAddClass=function(){
            	window.location.href = "${ctx}/questAction.do?method=addClassForm";
			}
			toEditClass=function(classId){
            	window.location.href = "${ctx}/questAction.do?method=editClassForm&classId="+classId;
			}
			toDeleteClass=function(classId){
				if(confirm("确认删除该指标类别吗？")){
            		window.location.href = "${ctx}/questAction.do?method=deleteClass&classId="+classId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="指标类别管理"/>
		<display:table name="sessionScope.list" id="class" pagesize="18">
			<display:column property="row_num" title="序号" headerClass="subject" sortable="true"/>
			<display:column property="type_name" title="问卷类型" headerClass="subject"  sortable="true"/>
			<display:column property="class_name" title="指标类别" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toEditClass('<bean:write name="class" property="id"/>')">编辑</a> | 
				<a href="javascript:toDeleteClass('<bean:write name="class" property="id"/>')">删除</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddClass()">添加</html:button>
		</div>
	</body>
</html>
