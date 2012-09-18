<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var jQuery=$;
</script>
<link href="${ctx}/js/jQuery/pikachoose/styles/bottom.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/js/jQuery/pikachoose/lib/jquery.pikachoose.js"></script>
<script type="text/javascript" src="${ctx }/bs/fault/js/fault_common.js"></script>
<script language="javascript" type="text/javascript">
	function check(){
		return true;
	}
</script>
<body>
	<template:titile value="故障回单审核" />
	<s:form action="faultAuditAction_audit" name="form" method="post"
		onsubmit="return check()">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<c:if test="${fault_alert.findType=='B16' }">
				<tr class="trwhite">
					<td class="tdulright" colspan="4" style="width: 100%">
						<b>故障图片</b>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulright" colspan="4"
						style="width: 100%; padding: 5px; text-align: center;"
						id="photosTd">
					</td>
				</tr>
			</c:if>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>故障单信息</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障标题：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input name="faultAudit.workflowTaskId"
						value="${faultAudit.workflowTaskId}" type="hidden" />
					<input name="businessType" value="${businessType }" type="hidden" />
					<input name="faultAudit.taskId" value="${fault_dispatch.id }"
						type="hidden" />
					${fault_alert.troubleTitle }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					发现方式：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="dic" id="${fault_alert.findType}"
						dicType="FIND_TYPE"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					EMOS单号：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_alert.eomsId }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障发生时间：
				</td>
				<td class="tdulright" style="width: 35%">
					<fmt:formatDate value="${fault_alert.troubleTime }"
						pattern="yyyy-MM-dd HH:mm:ss" var="troubleTime" />
					${troubleTime }
				</td>
				<td class="tdulleft" style="width: 15%">
					是否紧急故障：
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${fault_alert.isInstancy==1 }">是</c:if>
					<c:if test="${fault_alert.isInstancy==2 }">否</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障级别：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="dic" id="${fault_alert.troubleLevel}"
						dicType="TROUBLE_LEVEL"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					处理期限：
				</td>
				<td class="tdulright" style="width: 35%">
					<fmt:formatDate value="${fault_dispatch.deadline }"
						pattern="yyyy-MM-dd HH:mm:ss" var="deadline" />
					${deadline }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障描述：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${fault_alert.troubleDesc }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障站点：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_alert.stationName }
				</td>
				<td class="tdulleft" style="width: 15%">
					故障地点：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_alert.address }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					代维公司：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_dispatch.maintenanceName }
				</td>
				<td class="tdulleft" style="width: 15%">
					维护组：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_dispatch.patrolGroupName }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					派单人：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_dispatch.createrName }
				</td>
				<td class="tdulleft" style="width: 15%">
					派单时间：
				</td>
				<td class="tdulright" style="width: 35%">
					<fmt:formatDate value="${fault_dispatch.sendTime }"
						pattern="yyyy-MM-dd HH:mm:ss" var="sendTime" />
					${sendTime }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					联系电话：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${fault_dispatch.phone }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					备注：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${fault_dispatch.remark }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>故障处理过程</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4"
					style="width: 100%; padding: 5px; text-align: center;"
					id="processHistoryTd">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>故障现场图片</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4"
					style="width: 100%; padding: 5px; text-align: center;"
					id="processPhotosTd">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>故障报告信息</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障设备：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_reply.equipment }
				</td>
				<td class="tdulleft" style="width: 15%">
					是否集团业务：
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${fault_reply.isGroupbusiness==1 }">是</c:if>
					<c:if test="${fault_reply.isGroupbusiness==2 }">否</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					业务影响：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_reply.influenceBusiness }
				</td>
				<td class="tdulleft" style="width: 15%">
					网络分类：
				</td>
				<td class="tdulright" style="width: 35%">
					${fault_reply.networkType }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障现象：
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					${fault_reply.phenomena }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障原因：
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					${fault_reply.reason }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障解决方案：
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					${fault_reply.solution }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					遗留问题：
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					${fault_reply.leaveBehindProblem }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>故障回单审核信息</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4"
					style="width: 100%; padding: 5px; text-align: center;"
					id="auditHistoryTd">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					审核结果：
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					<input type="radio" name="faultAudit.isAuditing" value="pass"
						checked />
					通过
					<input type="radio" name="faultAudit.isAuditing" value="reject" />
					不通过
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					审核备注：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea name="faultAudit.remark" class="inputtext"
						style="width: 780px; height: 80px;"></textarea>
				</td>
			</tr>
		</table>
		<table width="100%" style="border: 0px">
			<tr class="trwhite">
				<td colspan="4" style="text-align: center; border: 0px">
					<html:submit styleClass="button">审核</html:submit>
					<input type="button" class="button" onclick="history.back()"
						value="返回">
				</td>
			</tr>
		</table>
	</s:form>
	<script type="text/javascript">
	setContextPath("${ctx}");
	showFaultPhotos('${fault_alert.id }');
	showProcessHistory('${fault_dispatch.id }');
	showProcessPhotos('${fault_dispatch.id }');
	showAuditHistory('${fault_dispatch.id }');
	</script>
</body>