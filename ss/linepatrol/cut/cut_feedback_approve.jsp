<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>��ӷ�������</title>
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
		<template:titile value="��ӷ�������"/>
		<html:form action="/cutFeedbackAction.do?method=addCutFeedbackApprove" onsubmit="return checkForm()">
			<jsp:include page="cut_apply_base.jsp"/>
			<c:if test="${cutFeedback.feedbackType=='0'}">
				<jsp:include page="cut_feedback_base.jsp"/>	
			</c:if>
			<c:if test="${cutFeedback.feedbackType=='1'}">
				<table cellspacing="0" cellpadding="1" align="center" style="width:90%; border-top:0px;" class="tabout">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">ȡ��ԭ��</td>
						<td class="tdulright" colspan="3"><c:out value="${cutFeedback.cancelCause}"/></td>
					</tr>
				</table>
			</c:if>	
			<input type="hidden" name="proposer" value="<c:out value='${cut.proposer }'/>" />
			<input type="hidden" name="id" value="<c:out value='${cutFeedback.id }'/>" />
			<input type="hidden" name="cutId" value="<c:out value='${cutFeedback.cutId }'/>" />
			<fmt:formatDate  value="${cutFeedback.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
			<fmt:formatDate  value="${cutFeedback.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
			<input type="hidden" value="${operater }" id="operater" name="operator" />
			<input type="hidden" value="${cutFeedback.feedbackType }" name="feedbackType"/>
			<table cellspacing="0" cellpadding="1" align="center" style="width:90%; border-top:0px;" class="tabout">
				<c:if test="${operater=='approve'}">
					<tr class="trcolor">
						<td style="width:20%" class="tdulleft">
							��˽����
						</td>
						<td colspan="3" class="tdulright">
							<input type="radio" name="approveResult" value="1" checked />
							ͨ��
							<input type="radio" name="approveResult" value="0" />
							��ͨ��
						</td>
					</tr>
					<tr class="trcolor">
						<td style="width:20%" class="tdulleft">
							��������
						</td>
						<td colspan="3" class="tdulright">
							<textarea name="approveRemark" class="textarea" rows="3"></textarea>
						</td>
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center">
							<html:submit property="action" styleClass="button">�ύ</html:submit>&nbsp;&nbsp;
							<html:reset property="action" styleClass="button">��д</html:reset>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
						</td>
					</tr>
				</c:if>
				<c:if test="${operater=='transfer'}">
					<tr class="trcolor">
						<apptag:approverselect label="ת����" colSpan="4" inputName="approvers,mobiles" inputType="radio" spanId="approverSpan" 
								approverType="transfer" objectId="${cut.id }" objectType="LP_CUT" />
					</tr>
					<tr class="trcolor">
						<td height="25" style="text-align: right;" class="tdulleft" style="width:20%;">
							ת��˵����<input type="hidden" name="approveResult" value="2" />
						</td>								   
						<td colspan="3" style="text-align: left;" class="tdulright">
							<textarea name="approveRemark" rows="6" style="width: 500px;"></textarea>
						</td>
					</tr>
					<tr class="trcolor">
						<td colspan="4" align="center">
							<html:submit property="action" styleClass="button">�ύ</html:submit>&nbsp;&nbsp;
							<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
						</td>
					</tr>
				</c:if>
			</table>
		</html:form>
	</body>
</html>
