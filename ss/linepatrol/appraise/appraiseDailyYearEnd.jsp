<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<title>���ռ����</title>
		<script type="text/javascript">
			function save(){
				if(confirm("�ύ�����޸ģ��Ƿ�ȷ���ύ��")){
					location.href="${ctx}/appraiseDailyYearEndAction.do?method=saveAppraiseDailyYearEnd";
				}
			}
		</script>
	</head>

	<body>
		<template:titile value="���ռ����" />
		<apptag:appraiseDailyYearEnd contractorId="${contractorId}" year="${year}" contractNo="${contractNo}" displayType="input" tableStyle="width:90%; border-top: 0px;"></apptag:appraiseDailyYearEnd>
		<div align="center"><input type="button" value="ȷ��" class="button" onclick="javascript:save();"><input type="button" value="����" class="button" onclick="javascript:history.back();"></div>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
