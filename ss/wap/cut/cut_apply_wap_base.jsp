<%@include file="/wap/header.jsp"%>
<html>
	<head>
		<title>��������������</title>
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
		������ƣ�
		<c:out value="${cut.cutName}" />
		<br />
		��Ӽ���
		<c:if test="${cut.cutLevel=='1'}">һ����</c:if>
		<c:if test="${cut.cutLevel=='2'}">�������</c:if>
		<c:if test="${cut.cutLevel=='3'}">Ԥ���</c:if>
		<br />
		�ֳ������ˣ�
		<c:out value="${cut.liveChargeman}" />
		<br />
		��ά�������ˣ�
		<c:out value="${cut.chargeMan}" />
		<br />
		Ԥ���
		<fmt:formatNumber value="${cut.budget}" pattern="#.##" var="budget" />
		<c:out value="${budget}" />
		Ԫ
		�Ƿ����ⲹ��
		<c:if test="${cut.isCompensation=='1'}">
			��
		</c:if>
		<c:if test="${cut.isCompensation=='0'}">
			��
		</c:if>
		<br />
		<c:if test="${cut.isCompensation=='1'}">
			�ⲹ��λ��
			<c:out value="${cut.compCompany}" />
		</c:if>
		<br />
		��ӵص㣺
		<c:out value="${cut.cutPlace}" />
		<br />
		�������ʱ�䣺
		<c:out value="${formatBeginTime}" />
		-
		<c:out value="${formatEndTime}" />
		<br />
		<c:if test="${not empty cut.replyBeginTime}">
			����ʱ�䣺
			<c:out value="${formatReplyBeginTime}" />
			-
			<c:out value="${formatReplyEndTime}" />
		</c:if>
		<br />
		�м̶����ƣ�
		<c:if test="${empty sublineIds}">
			���м̶�
		</c:if>
		<c:if test="${not empty sublineIds}">
			<apptag:trunk id="trunk" state="view_simple" value="${sublineIds}" />
		</c:if>
		<br />
		δ��ά�м̶Σ�
		<c:out value="${cut.otherImpressCable}" />
		<br />
		���ԭ��
		<c:out value="${cut.cutCause}" />
		<br />
		�����ύʱ�ޣ�
		<bean:write name="cut" property="deadline"
			format="yyyy/MM/dd HH:mm:ss" />
		<br />
	</body>
</html>
