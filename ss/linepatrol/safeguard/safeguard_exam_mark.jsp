<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>保障评分</title>
		<script type="text/javascript">
			function checkForm(){
				if(!checkAppraiseDailyValid()){
	  				return false;
	  			}
	  			submitForm1.submit();
			}
			function valCharLength(Value){
			     var j=0;
			     var s = Value;
			     for(var i=0; i<s.length; i++) {
					if (s.substr(i,1).charCodeAt(0)>255) {
						j  = j + 2;
					} else { 
						j++;
					}
			     }
			     return j;
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
		<template:titile value="保障考核"/>
		<html:form action="/safeguardExamAction.do?method=examSafeguard" styleId="submitForm1" enctype="multipart/form-data">&nbsp; 
			<input type="hidden" name="planId" value="${safeguardPlan.id }"/>
			<input type="hidden" name="taskId" value="${safeguardTask.id }"/>
			<input type="hidden" name="conId" value="${safeguardPlan.contractorId }"/>
			<div id="applyinfo" style="display: none;">
			<jsp:include page="safeguard_task_base.jsp"></jsp:include>
			<jsp:include page="safeguard_plan_base.jsp"></jsp:include>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<tr class="trcolor">
					<td colspan="4">
						<c:if test="${safeguardPlan.makeDate>safeguardTask.deadline}">
							<font color="red">保障方案提交超时，超时<bean:write name="planDays" format="#.##"/>小时</font>
						</c:if>
						<c:if test="${safeguardPlan.makeDate<=safeguardTask.deadline}">
							<font color="blue">保障方案提交提前，提前<bean:write name="planDays" format="#.##"/>小时</font>
						</c:if>
					</td>
				</tr>
			</table>
			<jsp:include page="safeguard_summary_base.jsp"></jsp:include>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<tr class="trcolor">
					<td colspan="4">
						<c:if test="${safeguardSummary.sumDate>safeguardPlan.deadline}">
							<font color="red">保障总结提交超时，超时<bean:write name="sumDays" format="#.##"/>小时</font>
						</c:if>
						<c:if test="${safeguardSummary.sumDate<=safeguardPlan.deadline}">
							<font color="blue">保障总结提交提前，提前<bean:write name="sumDays" format="#.##"/>小时</font>
						</c:if>
					</td>
				</tr>
			</table>
			</div>
		<div align="right" style="height: 30px; line-height: 30px; width:90%; margin:0px auto;"><a onclick="showOrHiddeInfo();" style="cursor: pointer;">显示/隐藏</a></div>
		</html:form>
		<apptag:appraiseDailyDaily businessId="${safeguardPlan.id}" contractorId="${safeguardPlan.contractorId}" businessModule="safeguard" tableClass="tabout" tableStyle="width:90%;"></apptag:appraiseDailyDaily>
			<apptag:appraiseDailySpecial businessId="${safeguardPlan.id}" contractorId="${safeguardPlan.contractorId}" businessModule="safeguard" tableClass="tabout" tableStyle="width:90%;"></apptag:appraiseDailySpecial>
			<div align="center" style="height:40px">
				<html:button property="action" styleClass="button" styleId="subbtn"
							onclick="checkForm()">提交</html:button>&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">重写</html:reset>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
	</body>
</html>
