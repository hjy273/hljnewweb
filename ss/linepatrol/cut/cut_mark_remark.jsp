<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>考核评分</title>
		<script type="text/javascript">
			function showAndHideCutFeedback(){
				var showCutFeedback = document.getElementById("showCutFeedback");
				var cutFeedbackDetailInfo = document.getElementById("cutFeedbackDetailInfo");
				if(showCutFeedback.innerText=="显示反馈信息"){
					cutFeedbackDetailInfo.style.display="block";
					showCutFeedback.innerText="隐藏反馈信息";
				}else{
					cutFeedbackDetailInfo.style.display="none";
					showCutFeedback.innerText="显示反馈信息";
				}
			}
			function checkForm(){
				if(!checkAppraiseDailyValid()){
	  				return false;
	  			}
	  			examForm.submit();
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
	<%
		Map map = (Map)request.getAttribute("map");
		Cut cut = (Cut)map.get("cut");
		CutFeedback cutFeedback = (CutFeedback)map.get("cutFeedback");
		CutAcceptance cutAcceptance = (CutAcceptance)map.get("cutAcceptance");
		String sublineIds = (String)map.get("sublineIds");
		List approve_info_list = (List)map.get("approve_info_list");
		String contractorId = (String)map.get("contractorId");
		request.setAttribute("cut",cut);
		request.setAttribute("cutFeedback",cutFeedback);
		request.setAttribute("cutAcceptance",cutAcceptance);
		request.setAttribute("sublineIds",sublineIds);
		request.setAttribute("approve_info_list", approve_info_list);
		request.setAttribute("contractorId", contractorId);
	%>
	<body>
		<template:titile value="割接考核"/>
		<html:form action="/checkAndMarkAction.do?method=checkAndMark" styleId="examForm">
			<input type="hidden" name="cutId" value="<c:out value='${cut.id }'/>" />
			<div id="applyinfo" style="display: none;">
			<jsp:include page="cut_apply_base.jsp"/>
			<jsp:include page="cut_feedback_base.jsp"/>
			<jsp:include page="cut_acceptance_base.jsp"/>
			</div>
		<div align="right" style="height: 30px; line-height: 30px; width:90%; margin:0px auto;"><a onclick="showOrHiddeInfo();" style="cursor: pointer;">显示/隐藏</a></div>
		</html:form>
		<apptag:appraiseDailyDaily businessId="${cutFeedback.id}" contractorId="${contractorId}" businessModule="lineCut" tableClass="tabout" tableStyle="width:90%;border-top:0px;"></apptag:appraiseDailyDaily>
			<apptag:appraiseDailySpecial businessId="${cutFeedback.id}" contractorId="${contractorId}" businessModule="lineCut" tableClass="tabout" tableStyle="width:90%;border-top:0px;"></apptag:appraiseDailySpecial>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-top:0px;" class="tabout">
				<tr class="trcolor">
					<td colspan="4" align="center">
						<html:button property="action" styleClass="button" styleId="subbtn"
							onclick="checkForm()">提交</html:button>&nbsp;&nbsp;
						<html:reset property="action" styleClass="button">重写</html:reset>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
					</td>
				</tr>
			</table>
	</body>
</html>
