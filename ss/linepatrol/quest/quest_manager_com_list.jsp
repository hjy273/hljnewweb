<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>参评对象管理</title>
		<script type="text/javascript">
			toAddCom=function(){
            	window.location.href = "${ctx}/questAction.do?method=addComForm";
			}
			toEditCom=function(comId){
            	window.location.href = "${ctx}/questAction.do?method=editComForm&comId="+comId;
			}
			toDeleteCom=function(comId){
				if(confirm("确认删除该参评对象吗？")){
            		window.location.href = "${ctx}/questAction.do?method=deleteCom&comId="+comId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="参评对象管理"/>
		<display:table name="sessionScope.list" id="com" pagesize="18">
			<display:column property="row_num" title="序号" headerClass="subject" sortable="true"/>
			<display:column property="com_name" title="参评对象" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toEditCom('<bean:write name="com" property="id"/>')">编辑</a> | 
				<a href="javascript:toDeleteCom('<bean:write name="com" property="id"/>')">删除</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddCom()">添加</html:button>
		</div>
	</body>
</html>
