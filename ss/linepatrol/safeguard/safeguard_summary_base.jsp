<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>制定保障方案基本数据</title>
	</head>
	<body>
		<table align="center" cellpadding="1" style="border-bottom:0px;" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">实际出动人员：</td>
				<td class="tdulright" style="width:30%;">
					<c:out value="${safeguardSummary.factResponder}"/> 人
				</td>
				<td class="tdulleft" style="width:20%;">实际出动车辆：</td>
				<td class="tdulright" style="width:30%;">
					<c:out value="${safeguardSummary.factRespondingUnit}"/> 辆
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">实际投入设备数：</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardSummary.equipmentNumber}"/> 套
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatDate  value="${safeguardSummary.realStartTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealStartTime"/>
				<fmt:formatDate  value="${safeguardSummary.realEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
				<td class="tdulleft" style="width:20%;">实际保障时间：</td>
				<td class="tdulright" colspan="3">
					<c:out value="${formatRealStartTime }"/> - <c:out value="${formatRealEndTime }"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障总结：</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardSummary.summary}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">总结附件：</td>
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
