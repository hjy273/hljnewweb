<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>演练方案基本数据</title>
	</head>
	<body>
		演练名称：
		<c:out value="${drillTask.name }" />
		<br />
		<fmt:formatDate value="${drillTask.beginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatBeginTime" />
		<fmt:formatDate value="${drillTask.endTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatEndTime" />
		建议演练时间：
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
		计划演练时间：
		<c:out value="${formatRealBeginTime }" />
		-
		<c:out value="${formatRealEndTime }" />
		<input type="hidden" value="<c:out value='${formatRealBeginTime }'/>"
			id="realBeginTime">
		<input type="hidden" value="<c:out value='${formatRealEndTime }'/>"
			id="realEndTime">
		<br />
		计划演练地点：
		<c:out value="${drillTask.locale }" />
		<br />
		实际演练地点：
		<c:out value="${drillPlan.address }" />
		<br />
		计划投入人数：
		<c:out value="${drillPlan.personNumber }" />
		人
		<br />
		计划投入车辆：
		<c:out value="${drillPlan.carNumber }" />
		辆
		<br />
		计划投入设备数：
		<c:out value="${drillPlan.equipmentNumber }" />
		套
		<br />
		场景设置：
		<c:out value="${drillPlan.scenario}" />
		<br />
		备注：
		<c:out value="${drillPlan.remark}" />
		<br />
		<c:if test="${not empty drillPlan.deadline }">
				总结提交时限：
				<bean:write name="drillPlan" property="deadline"
				format="yyyy/MM/dd HH:mm:ss" />
		</c:if>
	</body>
</html>
