<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>����������������</title>
	</head>
	<body>
		�������ƣ�
		<c:out value="${drillTask.name }" />
		<br />
		<fmt:formatDate value="${drillTask.beginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime" />
		<fmt:formatDate value="${drillTask.endTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime" />
		��������ʱ�䣺
		<c:out value="${formatBeginTime }" />
		-
		<c:out value="${formatEndTime }" />
		<input type="hidden" value="<c:out value='${formatBeginTime }'/>"
			id="beginTime">
		<input type="hidden" value="<c:out value='${formatEndTime }'/>"
			id="endTime">
		<br />
		<fmt:formatDate value="${drillPlan.realBeginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime" />
		<fmt:formatDate value="${drillPlan.realEndTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime" />
		�ƻ�����ʱ�䣺
		<c:out value="${formatRealBeginTime }" />
		-
		<c:out value="${formatRealEndTime }" />
		<input type="hidden" value="<c:out value='${formatRealBeginTime }'/>"
			id="realBeginTime">
		<input type="hidden" value="<c:out value='${formatRealEndTime }'/>"
			id="realEndTime">
		<br />
		�ƻ������ص㣺
		<c:out value="${drillTask.locale }" />
		<br />
		ʵ�������ص㣺
		<c:out value="${drillPlan.address }" />
		<br />
		�ƻ�Ͷ��������
		<c:out value="${drillPlan.personNumber }" />
		��
		<br />
		�ƻ�Ͷ�복����
		<c:out value="${drillPlan.carNumber }" />
		��
		<br />
		�ƻ�Ͷ���豸����
		<c:out value="${drillPlan.equipmentNumber }" />
		��
		<br />
		�������ã�
		<c:out value="${drillPlan.scenario}" />
		<br />
		��ע��
		<c:out value="${drillPlan.remark}" />
		<br />
		<c:if test="${not empty drillPlan.deadline }">
				�ܽ��ύʱ�ޣ�
				<bean:write name="drillPlan" property="deadline"
				format="yyyy/MM/dd HH:mm:ss" />
		</c:if>
	</body>
</html>
