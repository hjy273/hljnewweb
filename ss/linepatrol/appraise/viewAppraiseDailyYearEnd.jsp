<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���ռ���ֲ鿴</title>
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type="text/javascript">
			function back(){
				history.back();
			}
		</script>
	</head>

	<body>
		<template:titile value="���ռ���ֲ鿴" />
		<apptag:appraiseDailyYearEnd id="${id}" contractorId="${contractorId}" contractNo="${contractNo}" displayType="view" tableClass="tabout" tableStyle="width:100%"></apptag:appraiseDailyYearEnd>
		<div align="center"><input type="button" value="����" class="button" onclick="javascript:back();"></div>
	</body>
</html>
