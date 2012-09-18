<%@include file="/wap/header.jsp"%>
<html>
	<head>
		<title>割接申请基本数据</title>
	</head>
	<body>
		<fmt:formatDate value="${cut.beginTime}" pattern="yyyy/MM/dd HH:mm:ss"
			var="formatBeginTime" />
		<fmt:formatDate value="${cut.endTime}" pattern="yyyy/MM/dd HH:mm:ss"
			var="formatEndTime" />
		<fmt:formatDate value="${cut.replyBeginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyBeginTime" />
		<fmt:formatDate value="${cut.replyEndTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatReplyEndTime" />
		<fmt:formatDate value="${cut.applyDate}" pattern="yyyy/MM/dd HH:mm:ss"
			var="formatApplyDate" />
		割接名称：
		<c:out value="${cut.cutName}" />
		<br />
		割接级别：
		<c:if test="${cut.cutLevel=='1'}">一般割接</c:if>
		<c:if test="${cut.cutLevel=='2'}">紧急割接</c:if>
		<c:if test="${cut.cutLevel=='3'}">预割接</c:if>
		<br />
		现场负责人：
		<c:out value="${cut.liveChargeman}" />
		<br />
		线维区域负责人：
		<c:out value="${cut.chargeMan}" />
		<br />
		预算金额：
		<fmt:formatNumber value="${cut.budget}" pattern="#.##" var="budget" />
		<c:out value="${budget}" />
		元
		是否有赔补：
		<c:if test="${cut.isCompensation=='1'}">
			是
		</c:if>
		<c:if test="${cut.isCompensation=='0'}">
			否
		</c:if>
		<br />
		<c:if test="${cut.isCompensation=='1'}">
			赔补单位：
			<c:out value="${cut.compCompany}" />
		</c:if>
		<br />
		割接地点：
		<c:out value="${cut.cutPlace}" />
		<br />
		割接申请时间：
		<c:out value="${formatBeginTime}" />
		-
		<c:out value="${formatEndTime}" />
		<br />
		<c:if test="${not empty cut.replyBeginTime}">
			批复时间：
			<c:out value="${formatReplyBeginTime}" />
			-
			<c:out value="${formatReplyEndTime}" />
		</c:if>
		<br />
		中继段名称：
		<c:if test="${empty sublineIds}">
			无中继段
		</c:if>
		<c:if test="${not empty sublineIds}">
			<apptag:trunk id="trunk" state="view_simple" value="${sublineIds}" />
		</c:if>
		<br />
		未交维中继段：
		<c:out value="${cut.otherImpressCable}" />
		<br />
		割接原因：
		<c:out value="${cut.cutCause}" />
		<br />
		反馈提交时限：
		<bean:write name="cut" property="deadline"
			format="yyyy/MM/dd HH:mm:ss" />
		<br />
	</body>
</html>
