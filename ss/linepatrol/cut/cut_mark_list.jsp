<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<title>待评分列表</title>
		<script type="text/javascript">
			//查看验收结算
			toViewApply=function(cutId){
            	window.location.href = "${ctx}/checkAndMarkAction.do?method=viewApply&cutId="+cutId;
			}
			//考核评分
			toCutRemarkForm=function(cutId){
            	window.location.href = "${ctx}/checkAndMarkAction.do?method=checkAndMarkFrom&cutId="+cutId;
			}
		</script>
	</head>
	<body>
		<template:titile value="待评分列表"/>
		<display:table name="sessionScope.list" id="cut" pagesize="18">
			<display:column property="workorder_id" title="工单号" headerClass="subject"  sortable="true"/>
			<display:column property="cut_name" title="割接名称" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="割接级别" headerClass="subject"  sortable="true"/>
			<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>
			<display:column property="apply_date" title="申请时间" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="申请人" headerClass="subject"  sortable="true"/>
			<display:column property="cut_state" title="割接状态" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toViewApply('<bean:write name="cut" property="id"/>')">查看</a> | 
				<a href="javascript:toCutRemarkForm('<bean:write name="cut" property="id"/>')">考核评估</a>
			</display:column>
		</display:table>
	</body>
</html>
