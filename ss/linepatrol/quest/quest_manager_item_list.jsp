<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>指标项管理</title>
		<script type="text/javascript">
			toAddItem=function(){
            	window.location.href = "${ctx}/questAction.do?method=addItemForm";
			}
			toEditItem=function(itemId){
            	window.location.href = "${ctx}/questAction.do?method=editItemForm&itemId="+itemId;
			}
			toDeleteItem=function(itemId){
				if(confirm("确认删除该指标项吗？")){
            		window.location.href = "${ctx}/questAction.do?method=deleteManagerItem&itemId="+itemId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="指标项管理"/>
		<display:table name="sessionScope.list" id="item" pagesize="18">
			<display:column property="row_num" title="序号" headerClass="subject" sortable="true"/>
			<display:column property="sort_name" title="指标分类" headerClass="subject"  sortable="true"/>
			<display:column property="item_name" title="指标项" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toEditItem('<bean:write name="item" property="id"/>')">编辑</a> | 
				<a href="javascript:toDeleteItem('<bean:write name="item" property="id"/>')">删除</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddItem()">添加</html:button>
		</div>
	</body>
</html>
