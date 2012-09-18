<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>演练总结基本数据</title>
	</head>
	<body>
		演练名称：
		<c:out value="${drillTask.name}"></c:out>
		<br />
		<fmt:formatDate value="${drillTask.beginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime" />
		<fmt:formatDate value="${drillTask.endTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime" />
		计划演练时间：
		<c:out value="${formatBeginTime}"></c:out>
		-
		<c:out value="${formatEndTime}"></c:out>
		<input type="hidden" value="${formatBeginTime }" id="beginTime">
		<input type="hidden" value="${formatEndTime }" id="endTime">
		<br />
		计划提交时限：
		<bean:write name='drillTask' property='deadline'
			format='yyyy/MM/dd HH:mm:ss' />
		<br />
		<fmt:formatDate value="${drillPlan.realBeginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime" />
		<fmt:formatDate value="${drillPlan.realEndTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime" />
		实际演练时间：
		<c:out value="${formatRealBeginTime}"></c:out>
		-
		<c:out value="${formatRealEndTime}"></c:out>
		<input type="hidden" value="${formatRealBeginTime }"
			id="realBeginTime">
		<input type="hidden" value="${formatRealEndTime }" id="realEndTime">
		<br />
		总结提交时限：
		<bean:write name='drillPlan' property='deadline'
			format='yyyy/MM/dd HH:mm:ss' />
		<br />
		计划演练地点：
		<c:out value="${drillTask.locale}"></c:out>
		<br />
		实际演练地点：
		<c:out value="${drillPlan.address}"></c:out>
		<br/>
		计划投入人数：
		<c:out value="${drillPlan.personNumber}"></c:out>
		人
		<br />
		实际投入人数：
		<c:out value="${drillSummary.personNumber }" />
		人
		<br />
		计划投入车辆：
		<c:out value="${drillPlan.carNumber}"></c:out>
		辆
		<br />
		实际投入车辆：
		<c:out value="${drillSummary.carNumber }" />
		辆
		<br />
		计划投入设备数：
		<c:out value="${drillPlan.equipmentNumber}"></c:out>
		套
		<br />
		实际投入设备数：
		<c:out value="${drillSummary.equipmentNumber }" />
		套
		<br />
		场景设置：
		<c:out value="${drillPlan.scenario}"></c:out>
		<br />
		备注：
		<c:out value="${drillPlan.remark}"></c:out>
		<br />
		演练总结：
		<c:out value="${drillSummary.summary}" />
	</body>
</html>
