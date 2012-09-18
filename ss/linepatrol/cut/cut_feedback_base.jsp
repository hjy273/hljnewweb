<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>��ӷ�����������</title>
	</head>
	<body>
		<table cellspacing="0" cellpadding="1" align="center" style="width:90%;" class="tabout">
			<tr class="trcolor">
				<fmt:formatDate  value="${cutFeedback.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
				<fmt:formatDate  value="${cutFeedback.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
				<td class="tdulleft" style="width:20%;">ʵ�ʸ��ʱ�䣺</td>
				<td colspan="3" class="tdulright"><c:out value="${formatRealBeginTime}" /> - <c:out value="${formatRealEndTime}" /></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�Ƿ��ж�ҵ��</td>
				<td class="tdulright" style="width:30%;">
					<c:if test="${cutFeedback.isInterrupt=='1'}">��</c:if>
					<c:if test="${cutFeedback.isInterrupt=='0'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:20%;">�Ƿ���»�������־�ƣ�</td>
				<td class="tdulright" style="width:30%;">
					<c:if test="${cutFeedback.flag=='1'}">��</c:if>
					<c:if test="${cutFeedback.flag=='0'}">��</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatNumber value="${cutFeedback.bs}" pattern="#.#" var="bs"/>
				<td class="tdulleft" style="width:20%;">Ӱ��2G��վ��������</td>
				<td class="tdulright" style="width:30%;"><c:out value="${bs}" /> ��</td>
				<fmt:formatNumber value="${cutFeedback.td}" pattern="#.#" var="td"/>
				<td class="tdulleft" style="width:20%;">Ӱ��TDվ������</td>
				<td class="tdulright" style="width:30%;"><c:out value="${td}" /> ��</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatNumber value="${cutFeedback.cutTime}" pattern="#.##" var="cutTime"/>
				<td class="tdulleft" style="width:20%;">���ʱ����</td>
				<td class="tdulright" style="width:30%;"><c:out value="${cutTime}" /> Сʱ</td>
				<td class="tdulleft" style="width:20%;">�ƶ������ˣ�</td>
				<td class="tdulright" style="width:30%;"><c:out value="${cutFeedback.mobileChargeMan}" /></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�Ƿ�ʱ��</td>
				<td class="tdulright" colspan="3">
					<c:if test="${cutFeedback.isTimeOut=='1'}">��</c:if>
					<c:if test="${cutFeedback.isTimeOut=='0'}">��</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">���ʵ�����ϣ�</td>
				<td class="tdulright" colspan="3">
					<apptag:materialselect label="ʹ�ò���" materialUseType="Use" displayType="view" objectId="${cutFeedback.cutId }" useType="cut" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">��ӻ�������⣺</td>
				<td class="tdulright" colspan="3">
					<apptag:materialselect label="���ղ���" materialUseType="Recycle" displayType="view" objectId="${cutFeedback.cutId }" useType="cut" />
				</td>
			</tr>
			<c:if test="${cutFeedback.isTimeOut=='1'}">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">��ʱԭ��</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cutFeedback.timeOutCase}" />
					</td>
				</tr>
			</c:if>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">����ʵʩ�����</td>
				<td colspan="3" class="tdulright">
					<c:out value="${cutFeedback.implementation}" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�������⣺</td>
				<td colspan="3" class="tdulright">
					<c:out value="${cutFeedback.legacyQuestion}" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;" rowspan="2">����������</td>
				<td colspan="3" class="tdulright">
					<apptag:upload state="look" entityId="${cutFeedback.id}" entityType="LP_CUT_FEEDBACK" />
				</td>
			</tr>
			<tr class="trcolor">
				<td colspan="3">
					<apptag:image entityId="${cutFeedback.id}" entityType="LP_CUT_FEEDBACK"/>
				</td>
			</tr>
			<tr class="trcolor">
				<apptag:approve displayType="view" labelClass="tdulleft" valueClass="tdulright" objectId="${cutFeedback.id}" objectType="LP_CUT_FEEDBACK" colSpan="4" />
			</tr>
		</table>
	</body>
</html>
