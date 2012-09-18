<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>其他日常考核表</title>
		<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type="text/javascript">
			function checkAddInfo() {
  				if(!checkAppraiseDailyValid()){
  					return;
  				}
  				$('subbtn').disabled=true; 
  				saveEvaluate.submit();
  			}
		</script>
	</head>

	<body>
		<template:titile value="其他日常考核表" />
		<html:form action="/appraiseDailyOtherAction.do?method=saveAppraiseDailyOther" styleId="saveEvaluate">
		<apptag:appraiseDailyDaily businessId="" contractorId="${contractorId}" businessModule="other" 
						displayType="input" tableStyle="width:80%; border-top: 0px;"></apptag:appraiseDailyDaily>
		<div align="center">
			<html:button property="action" styleClass="button" styleId="subbtn"
							onclick="checkAddInfo()">提交</html:button>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="back" value="返回" class="button" onclick="history.back()"/>
		</div>
		
		</html:form>				
	</body>
</html>
