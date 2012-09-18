<%@include file="/wap/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>割接反馈审批</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="text/javascript">
	goBack = function() {
		var url = "${sessionScope.S_BACK_URL}";
		location = url;
	}
</script>
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
				if(document.getElementById("operater").value=="transfer"){
					if(document.getElementById("approvers").value.length==0){
						alert("转审人不能为空！");
						return false;
					}
					return true;
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="割接反馈审批" />
		<html:form
			action="/wap/cutFeedbackAction.do?method=addCutFeedbackApprove"
			onsubmit="return checkForm()">
			<jsp:include page="cut_apply_wap_base.jsp" />
			<c:if test="${cutFeedback.feedbackType=='0'}">
				<jsp:include page="cut_feedback_wap_base.jsp" />
			</c:if>
			<c:if test="${cutFeedback.feedbackType=='1'}">
				<td class="tdulleft" style="width: 20%;">
					取消原因：
				</td>
				<td class="tdulright" colspan="3">
					<c:out value="${cutFeedback.cancelCause}" />
				</td>
			</c:if>
			<input type="hidden" name="env" value="${env }" />
			<input type="hidden" name="proposer"
				value="<c:out value='${cut.proposer }'/>" />
			<input type="hidden" name="id"
				value="<c:out value='${cutFeedback.id }'/>" />
			<input type="hidden" name="cutId"
				value="<c:out value='${cutFeedback.cutId }'/>" />
			<fmt:formatDate value="${cutFeedback.beginTime}"
				pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime" />
			<fmt:formatDate value="${cutFeedback.endTime}"
				pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime" />
			<input type="hidden" value="${operater }" id="operater"
				name="operator" />
			<input type="hidden" value="${cutFeedback.feedbackType }"
				name="feedbackType" />
			<c:if test="${operater=='approve'}">
				<p>
					审核结果：
					<input type="radio" name="approveResult" value="1" checked />
					通过
					<input type="radio" name="approveResult" value="0" />
					不通过
					<br />
					审核意见：
					<textarea name="approveRemark" class="textarea" rows="3"></textarea>
				</p>
				<p>
					<html:submit property="action">提交</html:submit>
					<html:reset property="action">重写</html:reset>
					<html:button property="button" onclick="goBack();">返回待办</html:button>
				</p>
			</c:if>
			<c:if test="${operater=='transfer'}">
				<p>
					转审人：
					<c:if test="${sessionScope.APPROVER_INPUT_NAME_MAP!=null}">
						<c:forEach var="oneItem"
							items="${sessionScope.APPROVER_INPUT_NAME_MAP}">
							<c:if test="${oneItem.key!='span_value'}">
								<input name="${oneItem.key }" value="${oneItem.value }"
									type="hidden" />
							</c:if>
							<c:if test="${oneItem.key=='span_value'}">
							${oneItem.value }
						</c:if>
						</c:forEach>
					</c:if>
					<br />
					转审说明：
					<input type="hidden" name="approveResult" value="2" />
					<textarea name="approveRemark" rows="6" style="width: 500px;"></textarea>
				</p>
				<p>
					<html:submit property="action">提交</html:submit>
					<html:button property="button" onclick="goBack();">返回待办</html:button>
				</p>
			</c:if>
		</html:form>
	</body>
</html>
