<%@include file="/wap/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��ӷ�������</title>
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
				if(showCutFeedback.innerText=="��ʾ������Ϣ"){
					cutFeedbackDetailInfo.style.display="block";
					showCutFeedback.innerText="���ط�����Ϣ";
				}else{
					cutFeedbackDetailInfo.style.display="none";
					showCutFeedback.innerText="��ʾ������Ϣ";
				}
			}
			function checkForm(){
				if(document.getElementById("operater").value=="transfer"){
					if(document.getElementById("approvers").value.length==0){
						alert("ת���˲���Ϊ�գ�");
						return false;
					}
					return true;
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="��ӷ�������" />
		<html:form
			action="/wap/cutFeedbackAction.do?method=addCutFeedbackApprove"
			onsubmit="return checkForm()">
			<jsp:include page="cut_apply_wap_base.jsp" />
			<c:if test="${cutFeedback.feedbackType=='0'}">
				<jsp:include page="cut_feedback_wap_base.jsp" />
			</c:if>
			<c:if test="${cutFeedback.feedbackType=='1'}">
				<td class="tdulleft" style="width: 20%;">
					ȡ��ԭ��
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
					��˽����
					<input type="radio" name="approveResult" value="1" checked />
					ͨ��
					<input type="radio" name="approveResult" value="0" />
					��ͨ��
					<br />
					��������
					<textarea name="approveRemark" class="textarea" rows="3"></textarea>
				</p>
				<p>
					<html:submit property="action">�ύ</html:submit>
					<html:reset property="action">��д</html:reset>
					<html:button property="button" onclick="goBack();">���ش���</html:button>
				</p>
			</c:if>
			<c:if test="${operater=='transfer'}">
				<p>
					ת���ˣ�
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
					ת��˵����
					<input type="hidden" name="approveResult" value="2" />
					<textarea name="approveRemark" rows="6" style="width: 500px;"></textarea>
				</p>
				<p>
					<html:submit property="action">�ύ</html:submit>
					<html:button property="button" onclick="goBack();">���ش���</html:button>
				</p>
			</c:if>
		</html:form>
	</body>
</html>
