<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>考核评分基本页面</title>
	</head>
	<body>
		<apptag:appraiseDailyDaily businessId="${drillPlan.id}" contractorId="${drillPlan.contractorId}" businessModule="drill" displayType="view" tableClass="tabout"></apptag:appraiseDailyDaily>
		<apptag:appraiseDailySpecial businessId="${drillPlan.id}" contractorId="${drillPlan.contractorId}" businessModule="drill" displayType="view" tableClass="tabout"></apptag:appraiseDailySpecial>
	</body>
</html>
