<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/jQuery/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/jQuery/easyui/themes/icon.css">
<script type="text/javascript"
	src="${ctx }/js/jQuery/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx }/js/jQuery/easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="${ctx }/js/jQuery/easyui/easyloader.js"></script>	
<link href="${ctx}/js/jQuery/pikachoose/styles/bottom.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${ctx}/js/jQuery/pikachoose/lib/jquery.pikachoose.js"></script>
<script type="text/javascript" src="${ctx }/bs/fault/js/fault_common.js"></script>
<script language="javascript" type="text/javascript">
	function check() {
		return true;
	}
	Ext.onReady(function() {
		var actionUrl="${ctx}/common/externalresources_getOrgDeptUserJson.jspx?flag=2&node=&regionid=&objtype=ORG&orgtype=1&lv=3";
		jQuery("#faultReplyForm").validate();
		jQuery("#selectOrg").combotree({
			url : encodeURI(actionUrl),
			onClick : function(node) {
				jQuery("#faultReply_approver").val(node.id);
			}
		});
		setTimeout(function() {
			jQuery("#selectOrg").combotree("setValue", "${fault_dispatch.creater}");
		}, 1000);
	});
</script>
<body>
	<template:titile value="故障回单" />
	<s:form action="faultReplyAction_reply" id="faultReplyForm"
		name="replyForm" method="post" onsubmit="return check()">
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
					<input name="faultReply.id" value="${faultReply.id }" type="hidden" />
					<input name="businessType" value="${businessType }" type="hidden" />
					<input name="faultReply.workflowTaskId"
						value="${faultReply.workflowTaskId}" type="hidden" />
					<input name="faultReply.taskId" value="${fault_dispatch.id }"
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
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>故障报告信息</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障设备：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="faultReply.equipment"
						name="faultReply.equipment" value="${faultReply.equipment }"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					是否集团业务：
				</td>
				<td class="tdulright" style="width: 35%">
					<c:set var="checked_isgroupbusiness_no" value=""></c:set>
					<c:set var="checked_isgroupbusiness_yes" value="checked"></c:set>
					<c:if test="${faultReply.isGroupbusiness==1 }">
						<c:set var="checked_isgroupbusiness_no" value=""></c:set>
						<c:set var="checked_isgroupbusiness_yes" value="checked"></c:set>
					</c:if>
					<c:if test="${faultReply.isGroupbusiness==2 }">
						<c:set var="checked_isgroupbusiness_no" value="checked"></c:set>
						<c:set var="checked_isgroupbusiness_yes" value=""></c:set>
					</c:if>
					<input type="radio" id="faultReply.isGroupbusiness"
						name="faultReply.isGroupbusiness" value="1" ${checked_isgroupbusiness_yes } />
					是
					<input type="radio" id="faultReply.isGroupbusiness"
						name="faultReply.isGroupbusiness" value="2" ${checked_isgroupbusiness_no }/>
					否
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					业务影响：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="faultReply.influenceBusiness"
						name="faultReply.influenceBusiness"
						value="${faultReply.influenceBusiness }"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					网络分类：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="faultReply.networkType"
						name="faultReply.networkType" value="${faultReply.networkType }"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障现象：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea id="faultReply.phenomena" name="faultReply.phenomena"
						class="inputtext required" style="width: 780px; height: 80px;">${faultReply.phenomena }</textarea>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障原因：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea id="faultReply.reason" name="faultReply.reason"
						class="inputtext required" style="width: 780px; height: 80px;">${faultReply.reason }</textarea>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					故障解决方案：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea id="faultReply.solution" name="faultReply.solution"
						class="inputtext required" style="width: 780px; height: 80px;">${faultReply.solution }</textarea>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					遗留问题：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea name="faultReply.leaveBehindProblem" class="inputtext"
						style="width: 780px; height: 80px;">${faultReply.leaveBehindProblem }</textarea>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					提交审核人：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<div id="selectOrg"></div>
					<input id="faultReply_approver" name="faultReply.approver"
						value="${fault_dispatch.creater }" type="hidden" />
				</td>
			</tr>
		</table>
		<table width="100%" style="border: 0px">
			<tr class="trwhite">
				<td colspan="4" style="text-align: center; border: 0px;">
					<html:submit styleClass="button">回单</html:submit>
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