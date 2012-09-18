<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript">
	checkValid=function(addForm){
		var flag=judgeEmptyValue(addForm.validateResult,"审批结果");
		return flag;
	}
	addGoBackMod=function(){
		var url = "<%=request.getContextPath()%>/station_plan.do?method=<%=request.getAttribute("query_method")%>";
		self.location.replace(url);
	}
	</script>
	<body>
		<template:titile value="审核中继站计划" />
		<html:form action="/station_plan.do?method=auditing"
			styleId="auditingForm" method="post"
			onsubmit="return checkValid(this);">
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
				<template:formTr name="审批人">
					<input name="query_method" type="hidden"
						value="<%=request.getAttribute("query_method")%>" />
					<input name="objectId" type="hidden"
						value="<bean:write name="plan_bean" property="tid" />" />
					<input name="validateUserid" type="hidden"
						value="<%=((UserInfo) session.getAttribute("LOGIN_USER")).getUserID()%>" />
					<%=((UserInfo) session.getAttribute("LOGIN_USER")).getUserName()%>
				</template:formTr>
				<template:formTr name="审批结果">
					<html:select property="validateResult" styleClass="inputtext"
						style="width:250;">
						<html:option value="0">审核通过</html:option>
						<html:option value="1">审核不通过</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="备注">
					<html:textarea property="validateRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">提交</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">取消	</html:reset>
					</td>
					<td>
					  <input type="button" value="返回" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
						
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		</script>
	</body>
</html>
