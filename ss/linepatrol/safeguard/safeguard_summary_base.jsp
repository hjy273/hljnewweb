<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>�ƶ����Ϸ�����������</title>
	</head>
	<body>
		<table align="center" cellpadding="1" style="border-bottom:0px;" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">ʵ�ʳ�����Ա��</td>
				<td class="tdulright" style="width:30%;">
					<c:out value="${safeguardSummary.factResponder}"/> ��
				</td>
				<td class="tdulleft" style="width:20%;">ʵ�ʳ���������</td>
				<td class="tdulright" style="width:30%;">
					<c:out value="${safeguardSummary.factRespondingUnit}"/> ��
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">ʵ��Ͷ���豸����</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardSummary.equipmentNumber}"/> ��
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatDate  value="${safeguardSummary.realStartTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealStartTime"/>
				<fmt:formatDate  value="${safeguardSummary.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
				<td class="tdulleft" style="width:20%;">ʵ�ʱ���ʱ�䣺</td>
				<td class="tdulright" colspan="3">
					<c:out value="${formatRealStartTime }"/> - <c:out value="${formatRealEndTime }"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�����ܽ᣺</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardSummary.summary}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ܽḽ����</td>
				<td class="tdulright" colspan="3">
					<apptag:upload cssClass="" entityId="${safeguardSummary.id}" entityType="LP_SAFEGUARD_SUMMARY" state="look"/>
				</td>
			</tr>
			<tr class="trcolor">
				<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${safeguardSummary.id}" objectType="LP_SAFEGUARD_SUMMARY" colSpan="4" />
			</tr>
		</table>
	</body>
</html>
