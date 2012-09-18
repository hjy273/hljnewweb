<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>���̹����˲�ѯ</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<template:titile value="���̿��˲�ѯ" />
		<html:form action="/remedyExamAction.do?method=examList"
			styleId="submitForm1">
			<table border="1" align="center" cellspacing="0" cellpadding="1"
				class="tabout" width="80%">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						��Ŀ���ƣ�
					</td>
					<td class="tdulright">
						<html:text property="projectName"></html:text>
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
				toExamDispatch=function(id,contractorId){
	            	window.location.href = "${ctx}/remedyExamAction.do?method=examRemedyForm&apply_id="+id+"&contractorId="+contractorId;
				};
			</script>
			<%
				BasicDynaBean object = null;
				String id = "";
				String contractorId = "";
			%>
			<display:table name="sessionScope.examList" id="examBean" pagesize="18">
				<%
					object = (BasicDynaBean) pageContext.findAttribute("examBean");
					id = object.get("id") == null ? "" : (String)object.get("id");
					contractorId = object.get("contractorid") == null ? "" : (String)object.get("contractorid");
				%>
				<display:column property="projectname" title="��Ŀ����"
					headerClass="subject" sortable="true" />
				<display:column property="contractorname" title="ά����λ"
					headerClass="subject" sortable="true" />
				<display:column property="remedyaddress" title="�����ص�"
					headerClass="subject" sortable="true" />
				<display:column property="remedydate" title="����ʱ��"
					headerClass="subject" sortable="true" />
				<display:column property="remedyreson" title="ԭ��˵��"
					headerClass="subject" sortable="true" />
				<display:column property="remedysolve" title="������"
					headerClass="subject" sortable="true" />
				<display:column property="totalfee" title="���̷���"
					headerClass="subject" sortable="true" />
				<display:column media="html" title="����" >
					<a href="javascript:toExamDispatch('<%=id %>','<%=contractorId %>')">����</a>
				</display:column>
			</display:table>
			<div style="height: 50px; text-align: center;">
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</logic:notEmpty>
	</body>
</html>
