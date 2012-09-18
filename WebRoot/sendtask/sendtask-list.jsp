<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery-1.6.1.js"></script>
<script type="text/javascript"
	src="${ctx }/js/easyui/jquery.easyui.min.js"></script>
<script src="${ctx }/js/easyui/easyloader.js"></script>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript"
			src="${ctx }/sendtask/js/sendtask-common.js"></script>
		<script type="text/javascript"
			src="${ctx }/sendtask/js/sendtask-list.js"></script>
		<script type="" language="javascript">
		var contextPath="${ctx}";
		setRegionId("${LOGIN_USER.regionID}");
		</script>
	</head>
	<body>
		<!--显示所有派单页面-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<br>
		<template:titile value="任务派单列表" />
		<s:form action="sendTaskAction_list" method="post">
			<input name="is_query" value="1" type="hidden" />
			<table width="850" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						派单单位:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sel_send_orgs" style="width: 240px;"
							name="sendTask.sendorgid"></select>
					</td>
					<td class="tdulleft" style="width: 15%">
						派单人:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sel_send_users" style="width: 240px;"
							name="sendTask.senduserid"></select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						受理单位:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sel_accept_orgs" style="width: 240px;"
							name="sendTask.acceptorgid"></select>
					</td>
					<td class="tdulleft" style="width: 15%">
						受理人:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sel_accept_users" style="width: 240px;"
							name="sendTask.acceptuserid"></select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						派单时间:
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="sendTask.sendTimeStart"
							value="${sendTask.sendTimeStart}" readonly="readonly"
							class="Wdate" style="width: 110px"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						-
						<input type="text" name="sendTask.sendTimeEnd"
							value="${sendTask.sendTimeEnd}" readonly="readonly" class="Wdate"
							style="width: 110px"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					</td>
					<td class="tdulleft" style="width: 15%">
						派单类型:
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:quickLoadList cssClass="inputtext" style="width: 240px;"
							name="sendTask.sendtype" listName="dispatch_task" type="select"
							isRegion="false" keyValue="${sendTask.sendtype}" isQuery="query" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						派单标题:
					</td>
					<td class="tdulright" style="width: 35%">
						<input name="sendTask.sendtopic" style="width: 240px;"
							class="inputtext" value="${sendTask.sendtopic}" maxlength="100" />
					</td>
					<td class="tdulleft" style="width: 15%">
						派单状态:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sendTask.workstate" style="width: 240px;"
							name="sendTask.workstate">
							<option value="">
								不限
							</option>
							<option value="签收">
								待签收
							</option>
							<option value="回复">
								待回复
							</option>
							<option value="验证">
								待验证
							</option>
							<option value="0">
								重新派单
							</option>
						</select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="submit" value="查询" class="button">
			</div>
		</s:form>
		<c:if test="${sessionScope.SEND_TASK_LIST!=null}">
			<display:table name="sessionScope.SEND_TASK_LIST"
				requestURI="${ctx}/sendTaskAction_list.jspx" id="currentRowObject"
				pagesize="20" style="width:100%">
				<c:set var="dispatchId" value="${currentRowObject.id}"></c:set>
				<c:set var="taskId" value="${currentRowObject.task_id}"></c:set>
				<c:set var="sendTopic" value="${currentRowObject.sendtopic}"></c:set>
				<c:set var="workstate" value="${currentRowObject.workstate}"></c:set>
				<c:set var="sendUserId" value="${currentRowObject.senduserid}"></c:set>
				<display:column media="html" title="主题" sortable="true"
					maxLength="40">
					<a
						href="javascript:toViewForm('${dispatchId }','/sendTaskAction_list.jspx')">
						${sendTopic }</a>
				</display:column>
				<display:column property="sendtypelabel" title="类型"
					style="width:80px" maxLength="5" sortable="true" />
				<display:column property="sendorgname" title="派单单位"
					style="width:80px" sortable="true" />
				<display:column property="sendusername" title="派发人"
					style="width:60px" sortable="true" />
				<display:column property="sendtime" title="派发时间" style="width:140px"
					sortable="true" />
				<display:column property="nextprocessmanname" title="下步处理人"
					style="width:100px" sortable="true" />
				<display:column media="html" title="派单状态" style="width:100px">
					${workstate }
				</display:column>
				<display:column property="processterm" title="处理期限"
					style="width:140px" sortable="true" />
				<display:column media="html" title="操作" style="width:100px">
					<a
						href="javascript:toViewForm('${dispatchId}','/sendTaskAction_list.jspx')">查看</a>
					<c:if test="${workstate=='签收'&&sendUserId==LOGIN_USER.userID}">
						<a href="javascript:toRecycleForm('${dispatchId}','${taskId }')">收回</a>
					</c:if>
					<c:if test="${workstate=='0'&&sendUserId==LOGIN_USER.userID}">
						<a href="javascript:toUpdateForm('${dispatchId}')">重新派单</a>
					</c:if>
					<c:if test="${workstate=='派单'&&sendUserId==LOGIN_USER.userID}">
						<a
							href="javascript:toDeleteForm('${dispatchId}','/sendTaskAction_list.jspx')">删除</a>
					</c:if>
				</display:column>
			</display:table>
			<a href="#">导出为Excel文件</a>
		</c:if>
	</body>
	<script type="text/javascript">
	var values=['${sendTask.sendorgid}','${sendTask.senduserid}','${sendTask.acceptorgid}','${sendTask.acceptuserid}']
	setDefaultValues(values);
	</script>
</html>
