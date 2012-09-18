<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript">
	addGoBackMod=function() {
		var url = "<%=request.getContextPath()%>/station_plan.do?method=<%=(String)request.getAttribute("list_method")%>";
		self.location.replace(url);
	}
	</script>
	<body>
		<br>
		<template:titile value="查看中继站计划" />
		<template:formTable namewidth="150" contentwidth="350">
			<template:formTr name="计划名称">
				<bean:write name="plan_bean" property="planName" />
			</template:formTr>
			<template:formTr name="所属地州">
				<%=(String) request.getAttribute("region_name")%>
			</template:formTr>
			<template:formTr name="开始日期">
				<bean:write name="plan_bean" property="beginDate" />
			</template:formTr>
			<template:formTr name="结束日期">
				<bean:write name="plan_bean" property="endDate" />
			</template:formTr>
			<template:formTr name="计划类型">
				<logic:equal name="plan_bean" property="planType" value="01">
					波分日计划
				</logic:equal>
				<logic:equal name="plan_bean" property="planType" value="02">
					波分周计划
				</logic:equal>
				<logic:equal name="plan_bean" property="planType" value="03">
					波分月计划
				</logic:equal>
				<logic:equal name="plan_bean" property="planType" value="04">
					波分半年计划
				</logic:equal>
			</template:formTr>
			<template:formTr name="中继站">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<logic:iterate id="stationItem" name="station_list">
						<tr>
							<td>
								<bean:write name="stationItem" property="station_name" />
								<!-- 地图显示 -->
							</td>
						</tr>
					</logic:iterate>
				</table>
			</template:formTr>
			<template:formTr name="备注">
				<bean:write name="plan_bean" property="planRemark" />
			</template:formTr>
			<tr class="trcolor">
				<td align="right" width="150" class="tdulleft">
					审批信息
				</td>
				<td align="left" width="350" class="tdulleft">
					&nbsp;
				</td>
			</tr>
			<tr class="trcolor">
				<td align="center" width="500" colspan="2" id="applyAuditingTd" class="tdulright">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<logic:iterate id="auditingItem" name="auditing_history">
							<tr>
								<td>
									<bean:write name="auditingItem" property="username" />
								</td>
								<td>
									<logic:equal name="auditingItem" property="validate_result"
										value="0">
										审核通过
									</logic:equal>
									<logic:equal name="auditingItem" property="validate_result"
										value="1">
										审核不通过
									</logic:equal>
								</td>
								<td>
									<bean:write name="auditingItem" property="validate_time_dis" />
								</td>
								<td>
									<bean:write name="auditingItem" property="validate_remark" />
								</td>
							</tr>
						</logic:iterate>
					</table>
				</td>
			</tr>
			<template:formSubmit>
				<td>
					<input type="button" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
				</td>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>
