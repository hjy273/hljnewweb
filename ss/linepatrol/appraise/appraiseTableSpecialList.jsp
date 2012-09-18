<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<%@page import="com.cabletech.linepatrol.cut.dao.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel='stylesheet' type='text/css'
	href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript'
	src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>历年考核表</title>
		<script type="text/javascript">
	toViewForm = function(id) {
		window.location.href = "${ctx}/appraiseTableSpecialAction.do?method=viewTable&id="+id;
	}
	function toDelete(id){
		if(confirm("确定要删除该模板？")){
			location="${ctx}/appraiseTableSpecialAction.do?method=deleteTable&id="+id;
		}
	}
</script>

		<body>
			<template:titile value="历年专项考核表" />
			<display:table name="sessionScope.tables" id="table" pagesize="18" style="width:99%" >
				<logic:notEmpty name="table">
					<bean:define id="id" name="table" property="id" />
					<bean:define id="startDate" name="table" property="startDate" />
					<display:column property="tableName" title="考核表名" headerClass="subject" sortable="true" ></display:column>
					<display:column media="html" title="考核期限" headerClass="subject"   sortable="true" >
						<bean:write name="table" property="startDate" format="yyyy/MM/dd" />-<bean:write name="table" property="endDate" format="yyyy/MM/dd" />
					</display:column>
					<display:column media="html" title="制定日期"
						headerClass="subject" sortable="true">
							<bean:write name="table" property="createDate" format="yyyy-MM-dd" />
						</display:column>
					<display:column property="creater" title="制定人"
						headerClass="subject" sortable="true" />
					<display:column media="html" title="操作">
						<a href="javascript:toViewForm('${id}')">查看</a>
						<c:if test="${startDate > nowTime}">
						| <a href="javascript:toDelete('${id}')">删除</a>
						</c:if>
						| <html:link action="appraiseTableSpecialAction.do?method=exportTable&id=${id}">导出</html:link>
					</display:column>
				</logic:notEmpty>
			</display:table>
			
		</body>
</html>
