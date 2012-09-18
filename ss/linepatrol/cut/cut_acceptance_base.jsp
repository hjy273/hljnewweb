<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>割接验收基本数据</title>
	</head>
	<body>
		<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-bottom:0px;border-top:0px;" class="tabout">		
			<tr class="trcolor">
				<fmt:formatNumber value="${cutAcceptance.actualValue}" pattern="#.##" var="actualValue"/>
				<td class="tdulleft" style="width:20%;">结算金额：</td>
				<td class="tdulright" colspan="3"><c:out value="${actualValue }"/> 元</td>
			</tr>
			<tr class="trcolor">
				<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${cutAcceptance.id}" objectType="LP_CUT_ACCEPTANCE" colSpan="4"/>
			</tr>
		</table>
	</body>
</html>
