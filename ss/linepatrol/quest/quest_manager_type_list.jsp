<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>问卷类型管理</title>
		<script type="text/javascript">
			toAddType=function(){
            	window.location.href = "${ctx}/questAction.do?method=addTypeForm";
			}
			toEditType=function(typeId){
            	window.location.href = "${ctx}/questAction.do?method=editTypeForm&typeId="+typeId;
			}
			toDeleteType=function(typeId){
				if(confirm("确认删除该问卷类型吗？")){
            		window.location.href = "${ctx}/questAction.do?method=deleteType&typeId="+typeId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="问卷类型管理"/>
		<display:table name="sessionScope.list" id="type" pagesize="18">
			<display:column property="row_num" title="序号" headerClass="subject" sortable="true"/>
			<display:column property="type_name" title="问卷类型" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toEditType('<bean:write name="type" property="id"/>')">编辑</a> | 
				<a href="javascript:toDeleteType('<bean:write name="type" property="id"/>')">删除</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddType()">添加</html:button>
		</div>
	</body>
</html>
