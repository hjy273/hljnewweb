<%@include file="/wap/header.jsp"%>
<html>
	<head>
		<title>��ӷ�����������</title>
	</head>
	<body>
		<fmt:formatDate value="${cutFeedback.beginTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime" />
		<fmt:formatDate value="${cutFeedback.endTime}"
			pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime" />
		ʵ�ʸ��ʱ�䣺
		<c:out value="${formatRealBeginTime}" />
		-
		<c:out value="${formatRealEndTime}" />
		<br/>
		�Ƿ��ж�ҵ��
		<c:if test="${cutFeedback.isInterrupt=='1'}">��</c:if>
		<c:if test="${cutFeedback.isInterrupt=='0'}">��</c:if>
		<br/>
		�Ƿ���»�������־�ƣ�
		<c:if test="${cutFeedback.flag=='1'}">��</c:if>
		<c:if test="${cutFeedback.flag=='0'}">��</c:if>
		<br/>
		<fmt:formatNumber value="${cutFeedback.bs}" pattern="#.#" var="bs" />
		Ӱ��2G��վ��������
		<c:out value="${bs}" />
		��
		<br />
		<fmt:formatNumber value="${cutFeedback.td}" pattern="#.#" var="td" />
		Ӱ��TDվ������
		<c:out value="${td}" />
		��
		<br/>
		<fmt:formatNumber value="${cutFeedback.cutTime}" pattern="#.##"
			var="cutTime" />
		���ʱ����
		<c:out value="${cutTime}" />
		Сʱ 
		<br/>
		�ƶ������ˣ�
		<c:out value="${cutFeedback.mobileChargeMan}" />
		&nbsp;&nbsp;
		�Ƿ�ʱ��
		<c:if test="${cutFeedback.isTimeOut=='1'}">��</c:if>
		<c:if test="${cutFeedback.isTimeOut=='0'}">��</c:if>
		<p>
		���ʵ�����ϣ�
		<br/>
		<apptag:materialselect label="ʹ�ò���" materialUseType="Use"
			displayType="view_simple" objectId="${cutFeedback.cutId }"
			useType="cut" />
		</p>
		<p>
		��ӻ�������⣺
		</p>
		<apptag:materialselect label="���ղ���" materialUseType="Recycle"
			displayType="view_simple" objectId="${cutFeedback.cutId }"
			useType="cut" />
		<br/>
		��ʱԭ��
		<c:out value="${cutFeedback.timeOutCase}" />
		<br/>
		����ʵʩ�����
		<c:out value="${cutFeedback.implementation}" />
		<br/>
		�������⣺
		<c:out value="${cutFeedback.legacyQuestion}" />
		<br/>
		<apptag:image entityId="${cutFeedback.id}"
			entityType="LP_CUT_FEEDBACK" />
	</body>
</html>
