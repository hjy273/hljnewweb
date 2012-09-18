<%@include file="/common/header.jsp"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
<head>
	<title></title>
</head>
<body>
	<template:titile value="¿¼ºËÐÅÏ¢" />
	<apptag:appraiseDailyDaily businessId="${taskId}" contractorId="${contractorId}" businessModule="overHaul" displayType="view"></apptag:appraiseDailyDaily>
	<apptag:appraiseDailySpecial businessId="${taskId}" contractorId="${contractorId}" businessModule="overHaul" displayType="view"></apptag:appraiseDailySpecial>
	<div align="center"><input type="button" class="button" onclick="closeProcessWin();" value="¹Ø±Õ"></div>
</body>
