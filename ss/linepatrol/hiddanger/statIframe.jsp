<%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<table border=1 width=100%>
		<tr>
			<td align="center">�ƻ�����</td>
			<td align="center">��ʼʱ��</td>
			<td align="center">����ʱ��</td>
			<td align="center">Ѳ�������</td>
			<td align="center">���������</td>
			<td align="center">����</td>
		</tr>
	<c:forEach var="s" items="${plans}">
		<tr>
			<td align="center">&nbsp;${s['plan_name']}</td>
			<td align="center">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${s['start_date']}" /></td>
			<td align="center">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${s['end_date']}" /></td>
		<c:forEach var="w" items="${planStats}">
			<c:set var="p" value="--" />
			<c:set var="l" value="--" />
			<c:if test="${w.specPlanId eq s['plan_id']}">
				<c:set var="p" value="${w.patrolRatio}%" />
				<c:set var="l" value="${w.watchRatio}%" />
			</c:if>
		</c:forEach>
			<td align="center">&nbsp;${p}</td>
			<td align="center">&nbsp;${l}</td>
			<td align="center">&nbsp;
				<c:choose>
					<c:when test="${empty s['patrol_stat_state']}">
						δͳ��
					</c:when>
					<c:otherwise>
						<input type="button" value="�鿴" onclick="viewPlanStat('${s['plan_id']}')" />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
</table>