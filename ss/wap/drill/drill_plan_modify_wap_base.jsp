<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�����������</title>
	</head>
	<body>
		<c:if test="${not empty list}">
			<c:forEach items="${list}" var="drillPlanModify">
				<p>
					��������ʱ�䣺
					<fmt:formatDate value="${drillPlanModify.prevStartTime}"
						pattern="yyyy/MM/dd HH:mm:ss" var="formatPrevStartTime" />
					<fmt:formatDate value="${drillPlanModify.prevEndTime}"
						pattern="yyyy/MM/dd HH:mm:ss" var="formatPrevEndTime" />
					<c:out value="${formatPrevStartTime}"></c:out>
					�D
					<c:out value="${formatPrevEndTime}"></c:out>
					<br />
					�ƻ�����ʱ�䣺
					<fmt:formatDate value="${drillPlanModify.nextStartTime}"
						pattern="yyyy/MM/dd HH:mm:ss" var="formatNextStartTime" />
					<fmt:formatDate value="${drillPlanModify.nextEndTime}"
						pattern="yyyy/MM/dd HH:mm:ss" var="formatNextEndTime" />
					<c:out value="${formatNextStartTime}"></c:out>
					�D
					<c:out value="${formatNextEndTime}"></c:out>
					<br />
					���ԭ��
					<c:out value="${drillPlanModify.modifyCase}"></c:out>
				</p>
			</c:forEach>
		</c:if>
	</body>
</html>
