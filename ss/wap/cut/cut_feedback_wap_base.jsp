<%@include file="/wap/header.jsp"%>
<html>
	<head>
		<title>割接反馈基本数据</title>
	</head>
	<body>
		<fmt:formatDate value="${cutFeedback.beginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime" />
		<fmt:formatDate value="${cutFeedback.endTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime" />
		实际割接时间：
		<c:out value="${formatRealBeginTime}" />
		-
		<c:out value="${formatRealEndTime}" />
		<br/>
		是否中断业务：
		<c:if test="${cutFeedback.isInterrupt=='1'}">是</c:if>
		<c:if test="${cutFeedback.isInterrupt=='0'}">否</c:if>
		<br/>
		是否更新或增补标志牌：
		<c:if test="${cutFeedback.flag=='1'}">是</c:if>
		<c:if test="${cutFeedback.flag=='0'}">否</c:if>
		<br/>
		<fmt:formatNumber value="${cutFeedback.bs}" pattern="#.#" var="bs" />
		影响2G基站数据量：
		<c:out value="${bs}" />
		个
		<br />
		<fmt:formatNumber value="${cutFeedback.td}" pattern="#.#" var="td" />
		影响TD站数量：
		<c:out value="${td}" />
		个
		<br/>
		<fmt:formatNumber value="${cutFeedback.cutTime}" pattern="#.##"
			var="cutTime" />
		割接时长：
		<c:out value="${cutTime}" />
		小时 
		<br/>
		移动负责人：
		<c:out value="${cutFeedback.mobileChargeMan}" />
		&nbsp;&nbsp;
		是否超时：
		<c:if test="${cutFeedback.isTimeOut=='1'}">是</c:if>
		<c:if test="${cutFeedback.isTimeOut=='0'}">否</c:if>
		<p>
		割接实际用料：
		<br/>
		<apptag:materialselect label="使用材料" materialUseType="Use"
			displayType="view_simple" objectId="${cutFeedback.cutId }"
			useType="cut" />
		</p>
		<p>
		割接回收料入库：
		</p>
		<apptag:materialselect label="回收材料" materialUseType="Recycle"
			displayType="view_simple" objectId="${cutFeedback.cutId }"
			useType="cut" />
		<br/>
		超时原因：
		<c:out value="${cutFeedback.timeOutCase}" />
		<br/>
		方案实施情况：
		<c:out value="${cutFeedback.implementation}" />
		<br/>
		遗留问题：
		<c:out value="${cutFeedback.legacyQuestion}" />
		<br/>
		<apptag:image entityId="${cutFeedback.id}"
			entityType="LP_CUT_FEEDBACK" />
	</body>
</html>
