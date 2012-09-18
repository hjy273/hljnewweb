<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>�����ɵ�δ���˲�ѯ</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<c:if test="${examFlag=='unexam'}">
			<template:titile value="δ���˲�ѯ" />
		</c:if>
		<c:if test="${examFlag=='examed'}">
			<template:titile value="�ѿ��˲�ѯ" />
		</c:if>
		<html:form action="/dispatchExamAction.do?method=examList&examFlag=${examFlag}"
			styleId="submitForm1">
			<table border="1" align="center" cellspacing="0" cellpadding="1"
				class="tabout" width="80%">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						�ɵ����ƣ�
					</td>
					<td class="tdulright">
						<html:text property="sendtopic"></html:text>
					</td>
				</tr>
			</table>
			<div align="center">
				<html:submit property="action" styleClass="button">��ѯ</html:submit>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:reset property="action" styleClass="button">��д</html:reset>
			</div>
		</html:form>
		<template:displayHide styleId="submitForm1"></template:displayHide>
		<logic:notEmpty name="examList">
			<script type="text/javascript">
				toExamDispatch=function(dispatch_id,replyId,contractorId,examFlag){
	            	window.location.href = "${ctx}/dispatchExamAction.do?method=examDispatchForm&dispatch_id="+dispatch_id+"&replyId="+replyId+"&contractorId="+contractorId+"&examFlag="+examFlag;
				};
			</script>
			<%
				BasicDynaBean object = null;
				String replyId = "";
				String contractorId = "";
				String dispatchId = "";
			%>
			<display:table name="sessionScope.examList" id="examBean" pagesize="18">
				<%
					object = (BasicDynaBean) pageContext.findAttribute("examBean");
					replyId = object.get("reply_id") == null ? "" : (String)object.get("reply_id");
					contractorId = object.get("deptid") == null ? "" : (String)object.get("deptid");
					dispatchId = object.get("id") == null ? "" : (String)object.get("id");
				%>
				<display:column property="sendtopic" title="�ɵ�����"
					headerClass="subject" sortable="true" />
				<display:column property="sendtypelabel" title="�ɵ�����"
					headerClass="subject" sortable="true" />
				<display:column property="username" title="�ɵ���"
					headerClass="subject" sortable="true" />
				<display:column property="sendtime" title="�ɵ�ʱ��"
					headerClass="subject" sortable="true" />
				<display:column property="contractorname" title="���յ�λ"
					headerClass="subject" sortable="true" />
				<display:column media="html" title="����" >
					<c:if test="${examFlag=='unexam'}">
						<a href="javascript:toExamDispatch('<%=dispatchId %>','<%=replyId %>','<%=contractorId %>','${examFlag }')">����</a>
					</c:if>
					<c:if test="${examFlag=='examed'}">
						<a href="javascript:toExamDispatch('<%=dispatchId %>','<%=replyId %>','<%=contractorId %>','${examFlag }')">�鿴</a>
					</c:if>
				</display:column>
			</display:table>
		</logic:notEmpty>
	</body>
</html>
