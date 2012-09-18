<%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<table border=1 width=100%>
		<tr>
			<td align="center">计划名称</td>
			<td align="center">开始时间</td>
			<td align="center">结束时间</td>
			<td align="center">巡检完成率</td>
			<td align="center">盯防完成率</td>
			<td align="center">操作</td>
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
						未统计
					</c:when>
					<c:otherwise>
						<input type="button" value="查看" onclick="viewPlanStat('${s['plan_id']}')" />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
</table>