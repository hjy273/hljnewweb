<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>指标分类管理</title>
		<script type="text/javascript">
			toAddSort=function(){
            	window.location.href = "${ctx}/questAction.do?method=addSortForm";
			}
			toEditSort=function(sortId){
            	window.location.href = "${ctx}/questAction.do?method=editSortForm&sortId="+sortId;
			}
			toDeleteSort=function(sortId){
				if(confirm("确认删除该指标分类吗？")){
            		window.location.href = "${ctx}/questAction.do?method=deleteSort&sortId="+sortId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="指标分类管理"/>
		<display:table name="sessionScope.list" id="sort" pagesize="18">
			<display:column property="row_num" title="序号" headerClass="subject" sortable="true"/>
			<display:column property="class_name" title="指标类别" headerClass="subject"  sortable="true"/>
			<display:column property="sort_name" title="指标分类" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toEditSort('<bean:write name="sort" property="id"/>')">编辑</a> | 
				<a href="javascript:toDeleteSort('<bean:write name="sort" property="id"/>')">删除</a>
			</display:column>
		</display:table>
		<br/>
		<div style="height:40px">
			<html:button property="action" styleClass="button" onclick="toAddSort()">添加</html:button>
		</div>
	</body>
</html>
