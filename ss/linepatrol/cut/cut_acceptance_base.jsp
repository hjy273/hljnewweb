<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>������ջ�������</title>
	</head>
	<body>
		<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-bottom:0px;border-top:0px;" class="tabout">		
			<tr class="trcolor">
				<fmt:formatNumber value="${cutAcceptance.actualValue}" pattern="#.##" var="actualValue"/>
				<td class="tdulleft" style="width:20%;">�����</td>
				<td class="tdulright" colspan="3"><c:out value="${actualValue }"/> Ԫ</td>
			</tr>
			<tr class="trcolor">
				<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${cutAcceptance.id}" objectType="LP_CUT_ACCEPTANCE" colSpan="4"/>
			</tr>
		</table>
	</body>
</html>
