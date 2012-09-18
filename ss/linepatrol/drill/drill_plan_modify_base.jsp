<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>

<html>
	<head>
		<title>�����������</title>
	</head>
	<body>
		<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="drillPlanModify">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">���ǰʱ�䣺</td>
						<td class="tdulright">
							<fmt:formatDate  value="${drillPlanModify.prevStartTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatPrevStartTime"/>
							<fmt:formatDate  value="${drillPlanModify.prevEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatPrevEndTime"/>
							<c:out value="${formatPrevStartTime}"></c:out> �D <c:out value="${formatPrevEndTime}"></c:out>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">�����ʱ�䣺</td>
						<td class="tdulright">
							<fmt:formatDate  value="${drillPlanModify.nextStartTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatNextStartTime"/>
							<fmt:formatDate  value="${drillPlanModify.nextEndTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatNextEndTime"/>
							<c:out value="${formatNextStartTime}"></c:out> �D <c:out value="${formatNextEndTime}"></c:out>
						</td>
					</tr>
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">���ԭ��</td>
						<td class="tdulright">
							<c:out value="${drillPlanModify.modifyCase}"></c:out>
						</td>
					</tr>
					<tr class="trcolor">
						<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${drillPlanModify.id}" objectType="LP_DRILLPLAN_MODIFY" colSpan="2" />
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</body>
</html>
