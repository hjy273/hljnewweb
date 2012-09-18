<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>—›¡∑∆¿∑÷</title>
		<script type="text/javascript">
			function checkForm(){
				if(!checkAppraiseDailyValid()){
	  				return false;
	  			}
	  			processBar(submitForm1);
	  			submitForm1.submit();
			}
			
			function showOrHiddeInfo(){
				var infoDiv = document.getElementById("applyinfo");
				if(infoDiv.style.display=="block"){
					infoDiv.style.display="none";
				}else{
					infoDiv.style.display="block";
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="—›¡∑øº∫À"/>
		<html:form action="/drillExamAction.do?method=examDrill" styleId="submitForm1" enctype="multipart/form-data">
			<input type="hidden" name="taskId" value="<c:out value='${drillTask.id }' />"/>
			<input type="hidden" name="contractorId" value="<c:out value='${drillPlan.contractorId }' />"/>
			<input type="hidden" name="planId" value="<c:out value='${drillPlan.id }' />"/>
			<div id="applyinfo" style="display: none;">
			<jsp:include page="drill_summary_base.jsp"></jsp:include>
			</div>
			<div align="right" style="height: 30px; line-height: 30px; width:90%; margin:0px auto;"><a onclick="showOrHiddeInfo();" style="cursor: pointer;">œ‘ æ/“˛≤ÿ</a></div>
		</html:form>
		<apptag:appraiseDailyDaily businessId="${drillPlan.id }" contractorId="${drillPlan.contractorId }" businessModule="drill" tableClass="tabout" tableStyle="width:90%;" displayType="input"></apptag:appraiseDailyDaily>
			<apptag:appraiseDailySpecial businessId="${drillPlan.id }" contractorId="${drillPlan.contractorId }" businessModule="drill" tableClass="tabout" tableStyle="width:90%;" displayType="input"></apptag:appraiseDailySpecial>
			<div align="center" style="height:40px">
				<html:button property="action" styleClass="button" styleId="subbtn"
							onclick="checkForm()">Ã·Ωª</html:button> &nbsp;&nbsp;
				<html:reset property="action" styleClass="button">÷ÿ–¥</html:reset>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">∑µªÿ</html:button>
			</div>
	</body>
</html>


