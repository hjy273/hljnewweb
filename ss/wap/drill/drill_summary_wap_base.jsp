<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�����ܽ��������</title>
	</head>
	<body>
		�������ƣ�
		<c:out value="${drillTask.name}"></c:out>
		<br />
		<fmt:formatDate value="${drillTask.beginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime" />
		<fmt:formatDate value="${drillTask.endTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime" />
		�ƻ�����ʱ�䣺
		<c:out value="${formatBeginTime}"></c:out>
		-
		<c:out value="${formatEndTime}"></c:out>
		<input type="hidden" value="${formatBeginTime }" id="beginTime">
		<input type="hidden" value="${formatEndTime }" id="endTime">
		<br />
		�ƻ��ύʱ�ޣ�
		<bean:write name='drillTask' property='deadline'
			format='yyyy/MM/dd HH:mm:ss' />
		<br />
		<fmt:formatDate value="${drillPlan.realBeginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime" />
		<fmt:formatDate value="${drillPlan.realEndTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime" />
		ʵ������ʱ�䣺
		<c:out value="${formatRealBeginTime}"></c:out>
		-
		<c:out value="${formatRealEndTime}"></c:out>
		<input type="hidden" value="${formatRealBeginTime }"
			id="realBeginTime">
		<input type="hidden" value="${formatRealEndTime }" id="realEndTime">
		<br />
		�ܽ��ύʱ�ޣ�
		<bean:write name='drillPlan' property='deadline'
			format='yyyy/MM/dd HH:mm:ss' />
		<br />
		�ƻ������ص㣺
		<c:out value="${drillTask.locale}"></c:out>
		<br />
		ʵ�������ص㣺
		<c:out value="${drillPlan.address}"></c:out>
		<br/>
		�ƻ�Ͷ��������
		<c:out value="${drillPlan.personNumber}"></c:out>
		��
		<br />
		ʵ��Ͷ��������
		<c:out value="${drillSummary.personNumber }" />
		��
		<br />
		�ƻ�Ͷ�복����
		<c:out value="${drillPlan.carNumber}"></c:out>
		��
		<br />
		ʵ��Ͷ�복����
		<c:out value="${drillSummary.carNumber }" />
		��
		<br />
		�ƻ�Ͷ���豸����
		<c:out value="${drillPlan.equipmentNumber}"></c:out>
		��
		<br />
		ʵ��Ͷ���豸����
		<c:out value="${drillSummary.equipmentNumber }" />
		��
		<br />
		�������ã�
		<c:out value="${drillPlan.scenario}"></c:out>
		<br />
		��ע��
		<c:out value="${drillPlan.remark}"></c:out>
		<br />
		�����ܽ᣺
		<c:out value="${drillSummary.summary}" />
	</body>
</html>
