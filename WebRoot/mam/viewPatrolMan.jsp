<script language="javascript" src="${ctx}/js/validation/prototype.js"
	type=""></script>
<script type="text/javascript">
<!--
function toGetBack(){
        var url = "${ctx}/patrolAction.do?method=queryPatrol";
        self.location.replace(url);
}
//-->
</script>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<template:titile value="查看巡检维护组信息" />
	<template:formTable contentwidth="300" namewidth="200">
		<template:formTr name="巡检维护组">
			<bean:write name="patrolBean" property="patrolName" />
		</template:formTr>
		<template:formTr name="所属单位">
			<bean:write name="contractorName" />
		</template:formTr>
		<template:formTr name="维护区域">
			<bean:write name="patrolBean" property="maintenanceArea"  />
		</template:formTr>
		<template:formTr name="人员组成">
		</template:formTr>
		<tr>
			<td class="tdulleft" colspan="2"
				style="width: 100%; padding: 5px; text-align: center;">
				<table width="95%" border="0" align="center" cellpadding="3"
					cellspacing="0" class="tabout">
					<tr>
						<td class="tdulleft" style="width:20%;text-align: center;">员工编号</td>
						<td class="tdulleft" style="width:30%;text-align: center;">姓名</td>
						<td class="tdulleft" style="width:25%;text-align: center;">职务</td>
					</tr>
					<c:if test="${patrolmans!=null}">
						<c:forEach var="patrolman" items="${patrolmans}">
							<tr>
								<td class="tdulleft" style="width: 20%; text-align: center;">
									<bean:write name="patrolman" property="employee_num" />
								</td>
								<td class="tdulleft" style="width: 30%; text-align: center;">
									<bean:write name="patrolman" property="name" />
								</td>
								<td class="tdulleft" style="width: 25%; text-align: center;">
									<bean:write name="patrolman" property="jobinfo" />
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</td>
		</tr>
		<template:formTr name="负责人">
			<bean:write name="patrolBean" property="principal" />
		</template:formTr>
		<template:formTr name="联系电话">
			<bean:write name="patrolBean" property="phone" />
		</template:formTr>
		<template:formTr name="驻地地址">
			<bean:write name="patrolBean" property="address" />
		</template:formTr>
		<template:formTr name="驻地电话">
			<bean:write name="patrolBean" property="workPhone" />
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="button" class="button" onclick="toGetBack()" value="返回">
			</td>
		</template:formSubmit>
	</template:formTable>
