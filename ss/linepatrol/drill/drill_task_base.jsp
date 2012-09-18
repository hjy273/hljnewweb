<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>

<html>
	<head>
		<title>演练方案基本数据</title>
	</head>
	<body>
		<table align="center" cellpadding="1" cellspacing="0" style="border-bottom:0px;" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">任务名称：</td>
				<td class="tdulright">
					<c:out value="${drillTask.name}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">演练级别：</td>
				<td class="tdulright">
					<c:if test="${drillTask.level=='1'}">
						一般演练
					</c:if>
					<c:if test="${drillTask.level=='2'}">
						重点演练
					</c:if>
					<c:if test="${drillTask.level=='0'}">
						自定义演练
					</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">建议演练时间：</td>
				<td class="tdulright">
					<fmt:formatDate  value="${drillTask.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime"/>
					<fmt:formatDate  value="${drillTask.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime"/>
					<c:out value="${formatBeginTime}"/> - <c:out value="${formatEndTime}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">方案提交时限：</td>
				<td class="tdulright">
					<fmt:formatDate  value="${drillTask.deadline}" pattern="yyyy/MM/dd HH:mm:ss" var="formatDeadline"/>
					<c:out value="${formatDeadline}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">演练地点：</td>
				<td class="tdulright">
					<c:out value="${drillTask.locale}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">演练要求：</td>
				<td class="tdulright">
					<c:out value="${drillTask.demand}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">备注：</td>
				<td class="tdulright">
					<c:out value="${drillTask.remark}"></c:out>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">任务附件：</td>
				<td class="tdulright">
					<apptag:upload cssClass="" entityId="${drillTask.id}" entityType="LP_DRILLTASK" state="look"/>
				</td>
			</tr>
			<c:if test="${drillTask.cancelUserId!=null&&drillTask.cancelUserId!=''}">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						${drillTask.cancelUserName}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						${drillTask.cancelTimeDis}
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl">
						${drillTask.cancelReason}
					</td>
				</tr>
			</c:if>
		</table>
	</body>
</html>
