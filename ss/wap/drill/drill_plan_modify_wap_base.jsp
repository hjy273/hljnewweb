<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>演练方案变更</title>
	</head>
	<body>
		<c:if test="${not empty list}">
			<c:forEach items="${list}" var="drillPlanModify">
				<p>
					建议演练时间：
					<fmt:formatDate value="${drillPlanModify.prevStartTime}"
						pattern="yyyy/MM/dd HH:mm:ss" var="formatPrevStartTime" />
					<fmt:formatDate value="${drillPlanModify.prevEndTime}"
						pattern="yyyy/MM/dd HH:mm:ss" var="formatPrevEndTime" />
					<c:out value="${formatPrevStartTime}"></c:out>
					―
					<c:out value="${formatPrevEndTime}"></c:out>
					<br />
					计划演练时间：
					<fmt:formatDate value="${drillPlanModify.nextStartTime}"
						pattern="yyyy/MM/dd HH:mm:ss" var="formatNextStartTime" />
					<fmt:formatDate value="${drillPlanModify.nextEndTime}"
						pattern="yyyy/MM/dd HH:mm:ss" var="formatNextEndTime" />
					<c:out value="${formatNextStartTime}"></c:out>
					―
					<c:out value="${formatNextEndTime}"></c:out>
					<br />
					变更原因：
					<c:out value="${drillPlanModify.modifyCase}"></c:out>
				</p>
			</c:forEach>
		</c:if>
	</body>
</html>
