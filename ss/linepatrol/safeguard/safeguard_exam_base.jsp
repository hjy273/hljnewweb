<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />

<html>
	<head>
		<title>考核评分基本页面</title>
	</head>
	<body>
		<apptag:appraiseDailyDaily businessId="${safeguardPlan.id}"
			contractorId="${safeguardPlan.contractorId}"
			businessModule="safeguard" displayType="view" tableClass="tabout"
			tableStyle="width:90%;"></apptag:appraiseDailyDaily>
			<apptag:appraiseDailySpecial businessId="${safeguardPlan.id}"
			contractorId="${safeguardPlan.contractorId}"
			businessModule="safeguard" displayType="view" tableClass="tabout"
			tableStyle="width:90%;"></apptag:appraiseDailySpecial>
		<br>
		<br>
	</body>
</html>
