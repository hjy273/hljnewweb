<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>故障查询</title>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">

	</head>

	<body>
		<template:titile value="故障查询" />
		<div style="text-align: center;">
			<iframe
				src="${ctx}/troubleReplyAction.do?method=viewTroubleProcess&&forward=query_trouble_state&&task_names=${task_names }"
				frameborder="0" id="processGraphFrame" height="75" scrolling="no"
				width="95%"></iframe>
		</div>
		<html:form action="/troubleQueryStatAction.do?method=getTroubleInfos"
			styleId="queryTroubleInfo">
			<table width="100%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdulleft" width="20%">
						故障发生时间：
					</td>
					<td class="tdulright" colspan="5">
						<html:text property="startTimeBegin" name="troubleQueryStatBean" styleClass="Wdate" styleId="startTimeBegin"
							style="width: 105"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate: '%y-%M-%d-%H-%m-%s'})"
							readonly="true" />
						--
						<html:text property="startTimeEnd" name="troubleQueryStatBean" styleClass="Wdate" style="width: 105" styleId="startTimeEnd"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'startTimeBegin\')}',maxDate: '%y-%M-%d-%H-%m-%s'})"
							readonly="true" />
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						故障负责代维：
					</td>
					<td class="tdulright">
						<html:select property="contractorid" styleClass="inputtext" style="width:140px">
							<html:option value="">全部</html:option>
						<html:options collection="cons" property="contractorid" labelProperty="contractorname"></html:options>
						</html:select>
					</td>
					<td class="tdulleft" colspan="2">
						设备所属地：
					</td>
					<td class="tdulright" colspan="2">
						<apptag:quickLoadList cssClass="checkbox" id="termiAddr"
							name="termiAddr" listName="terminal_address" keyValue="${otherCon.termiAddr}" type="checkbox"></apptag:quickLoadList>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft">
						故障类型：
					</td>
					<td class="tdulright" colspan="2">
						<apptag:quickLoadList cssClass="checkbox" id="troubleType"
							name="troubleType" listName="lp_trouble_type" keyValue="${otherCon.troubleType}" type="checkbox"></apptag:quickLoadList>
					</td>
					<td class="tdulleft">
						是否为重大故障：
					</td>
					<td class="tdulright" colspan="2">
						<html:multibox property="isGreatTrouble" value="1" />是&nbsp;&nbsp;
						<html:multibox property="isGreatTrouble" value="0" />否
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						故障原因：
					</td>
					<td class="tdulright" colspan="5">
						<apptag:quickLoadList cssClass="checkbox" id="troublReason"
							name="troublReason" listName="trouble_reason_id" keyValue="${otherCon.troublReason}" type="checkbox"></apptag:quickLoadList>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdulleft">
						中继段：
					</td>
					<td class="tdulright" colspan="5">
						<apptag:trunk id="trunk" value="${otherCon.trunks}" state="edit"/>
					</td>
				</tr>
				<tr class=trwhite>
					<td class="tdulleft">
						eoms单号：
					</td>
					<td class="tdulright" colspan="2">
						<html:text property="eomscode" styleClass="inputtext" name="troubleQueryStatBean" styleId="eomscode" style="width: 225px" />
					</td>
					<td class="tdulleft">
						是否取消：
					</td>
					<td class="tdulright" colspan="2">
						<select name="troubleState" class="inputtext" style="width:140px">
							<option value="">不限</option>
							<option value="999">是</option>
							<option value="0">否</option>
						</select>
					</td>
			</table>
			<logic:present name="queryCondition" property="taskStates">
				<logic:iterate id="oneTaskState" name="queryCondition"
					property="taskStates">
					<input id="taskStates_<bean:write name="oneTaskState" />"
						type="hidden" name="taskStates"
						value="<bean:write name="oneTaskState" />" />
				</logic:iterate>
			</logic:present>
			<div align="center">
				<input type="hidden" name="isQuery" value="true" />
				<input name="action" class="button" value="查询" type="submit" />
			</div>
		</html:form>
		<template:displayHide styleId="queryTroubleInfo"></template:displayHide>

		<logic:notEmpty name="troubles">
			<script type="text/javascript" language="javascript">
	toViewForm = function(idValue) {
		window.location.href = "${ctx}/troubleQueryStatAction.do?method=viewTrouble&troubleid="
				+ idValue;
	}
	exportList = function() {
		//var url="${ctx}/";
		//self.location.replace(url);
	}
	function goBack() {
		var url = "${ctx}/troubleQueryStatAction.do?method=queryTroubleForm";
		self.location.replace(url);
	}
</script>
			<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
				media="screen, print" />
			<style type="text/css">
.subject {
	text-align: center;
}
</style>
			<%
				BasicDynaBean object = null;
					Object troubleid = null;
					Object tid = null;
					Object replyid = null;
			%>
			<display:table name="sessionScope.troubles" id="currentRowObject"
				pagesize="15">
				<display:column media="html" title="故障单编号" sortable="true">
					<%
						object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
									if (object != null) {
										troubleid = object.get("trouble_id");
										tid = object.get("id");
										replyid = object.get("replyid");
									}
					%>
					<a href="javascript:toViewForm('<%=tid%>')"><%=troubleid%></a>
				</display:column>
				<display:column property="trouble_name" title="故障名称"
					headerClass="subject" maxLength="15" sortable="true" />
				<display:column property="contractorname" title="事故处理单位"
					headerClass="subject" maxLength="9" sortable="true" />
				<display:column property="trunk" sortable="true" title="故障中继段"
					headerClass="subject" maxLength="18" />
				<display:column property="trouble_send_time" sortable="true"
					title="故障派发时间" headerClass="subject" />
				<display:column property="etime" title="抢修历时(小时)"
					headerClass="subject" sortable="true" />
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td>
						<logic:notEmpty name="troubles1">
							<a href="javascript:exportList()">导出为Excel文件</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="返回" onclick="goBack();"
							type="button" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	</body>
</html>
