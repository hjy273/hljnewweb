<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>割接反馈基本数据</title>
	</head>
	<body>
		<table cellspacing="0" cellpadding="1" align="center" style="width:90%;" class="tabout">
			<tr class="trcolor">
				<fmt:formatDate  value="${cutFeedback.beginTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealBeginTime"/>
				<fmt:formatDate  value="${cutFeedback.endTime}" pattern="yyyy/MM/dd HH:mm:ss" var="formatRealEndTime"/>
				<td class="tdulleft" style="width:20%;">实际割接时间：</td>
				<td colspan="3" class="tdulright"><c:out value="${formatRealBeginTime}" /> - <c:out value="${formatRealEndTime}" /></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">是否中断业务：</td>
				<td class="tdulright" style="width:30%;">
					<c:if test="${cutFeedback.isInterrupt=='1'}">是</c:if>
					<c:if test="${cutFeedback.isInterrupt=='0'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:20%;">是否更新或增补标志牌：</td>
				<td class="tdulright" style="width:30%;">
					<c:if test="${cutFeedback.flag=='1'}">是</c:if>
					<c:if test="${cutFeedback.flag=='0'}">否</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatNumber value="${cutFeedback.bs}" pattern="#.#" var="bs"/>
				<td class="tdulleft" style="width:20%;">影响2G基站数据量：</td>
				<td class="tdulright" style="width:30%;"><c:out value="${bs}" /> 个</td>
				<fmt:formatNumber value="${cutFeedback.td}" pattern="#.#" var="td"/>
				<td class="tdulleft" style="width:20%;">影响TD站数量：</td>
				<td class="tdulright" style="width:30%;"><c:out value="${td}" /> 个</td>
			</tr>
			<tr class="trcolor">
				<fmt:formatNumber value="${cutFeedback.cutTime}" pattern="#.##" var="cutTime"/>
				<td class="tdulleft" style="width:20%;">割接时长：</td>
				<td class="tdulright" style="width:30%;"><c:out value="${cutTime}" /> 小时</td>
				<td class="tdulleft" style="width:20%;">移动负责人：</td>
				<td class="tdulright" style="width:30%;"><c:out value="${cutFeedback.mobileChargeMan}" /></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">是否超时：</td>
				<td class="tdulright" colspan="3">
					<c:if test="${cutFeedback.isTimeOut=='1'}">是</c:if>
					<c:if test="${cutFeedback.isTimeOut=='0'}">否</c:if>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">割接实际用料：</td>
				<td class="tdulright" colspan="3">
					<apptag:materialselect label="使用材料" materialUseType="Use" displayType="view" objectId="${cutFeedback.cutId }" useType="cut" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">割接回收料入库：</td>
				<td class="tdulright" colspan="3">
					<apptag:materialselect label="回收材料" materialUseType="Recycle" displayType="view" objectId="${cutFeedback.cutId }" useType="cut" />
				</td>
			</tr>
			<c:if test="${cutFeedback.isTimeOut=='1'}">
				<tr class="trcolor">
					<td class="tdulleft" style="width:20%;">超时原因：</td>
					<td colspan="3" class="tdulright">
						<c:out value="${cutFeedback.timeOutCase}" />
					</td>
				</tr>
			</c:if>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">方案实施情况：</td>
				<td colspan="3" class="tdulright">
					<c:out value="${cutFeedback.implementation}" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">遗留问题：</td>
				<td colspan="3" class="tdulright">
					<c:out value="${cutFeedback.legacyQuestion}" />
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;" rowspan="2">反馈附件：</td>
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
