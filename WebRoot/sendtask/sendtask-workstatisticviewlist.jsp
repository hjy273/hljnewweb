<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<script type="text/javascript"
	src="${ctx}/sendtask/js/sendtask-common.js"></script>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript" src="/WebApp/js/prototype.js"></script>
		<script type="" language="javascript">
		var contextPath="${ctx}";
		</script>
	</head>
	<body>
		<!--显示一个派单详细信息页面-->
		<br>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<template:titile value="${sendTask.sendtopic}派单工程进度" />
		<table width="90%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class=trcolor>
				<td class="tdr" width="15%">
					工作类别：
				</td>
				<td class="tdl" width="35%">
					<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
						keyValue="${sendTask.sendtype}" type="look" isRegion="false"></apptag:quickLoadList>
				</td>
				<td class="tdr">
					派单时间：
				</td>
				<td class="tdl">
					${sendtime }
				</td>
			</tr>
			<!-- 
			<tr class=trcolor>
				<td class="tdr">
					派单部门：
				</td>
				<td class="tdl">
					${sendTask.sendorgname }
				</td>
				<td class="tdr">
					派 单 人：
				</td>
				<td class="tdl">
					${sendTask.sendusername }
				</td>
			</tr>
			 -->
			<tr class=trcolor>
				<td class="tdr">
					受理部门：
				</td>
				<td class="tdl">
					${sendTask.acceptorgname }
				</td>
				<td class="tdr">
					受理人：
				</td>
				<td class="tdl">
					${sendTask.acceptusername }
				</td>
			</tr>
			<!-- 
			<tr class=trcolor>
				<td class="tdr">
					任务主题：
				</td>
				<td class="tdl" colspan="3">
					${sendTask.sendtopic }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					任务说明：
				</td>
				<td class="tdl" colspan="3">
					${sendTask.sendtext }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					处理时限：
				</td>
				<td class="tdl" colspan="3">
					${processterm }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					任务附件：
				</td>
				<td class="tdl" colspan="3">
					<apptag:upload cssClass="" entityId="${sendTask.id}"
						entityType="COMMON_SENDTASK" state="look" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdc" colspan="4" id="processHistoryTd"
					style="padding: 5px;">
				</td>
			</tr>
			 -->
			<tr class=trcolor>
				<td class="tdl" colspan="4" style="padding: 5px;">
					<display:table name="ONE_SENDTASK_WORKSTATISTIC_LIST"
						id="currentRowObject" style="width:100%">
						<display:column property="workunit" title="施工单位"
							style="width:140px" sortable="true" />
						<display:column property="date" title="施工时间" style="width:80px"
							sortable="true" />
						<display:column property="datepointnum" title="采集点数"
							style="width:60px" sortable="true" />
						<display:column property="dateworknum" title="工作量（单位：米）"
							style="width:140px" sortable="true" />
						<display:column media="html" title="操作" style="width:100px">
							<a href="#">连接GIS</a>
						</display:column>
					</display:table>
				</td>
			</tr>
		</table>
		<p align="center">
			<input type="button" value="返回" class="button"
				onclick="goBack('${url}');">
		</p>
	</body>
</html>
