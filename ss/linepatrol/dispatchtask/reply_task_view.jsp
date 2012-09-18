<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
	</head>
	<body>
		<!--显示派单反馈的详细信息-->
		<br>
		<template:titile value="派单反馈详细信息" />
		<table width="90%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class=trcolor>
				<td class="tdr" height="40">
					反馈人：
				</td>
				<td class="tdl" width="35%">
					<bean:write name="replybean" property="replyusername" />
				</td>
				<td class="tdr">
					反馈结果：
				</td>
				<td class="tdl" width="35%">
					<c:if test="${replybean.replyresult=='00'}">未完成</c:if>
					<c:if test="${replybean.replyresult=='01'}">部分完成</c:if>
					<c:if test="${replybean.replyresult=='02'}">基本完成</c:if>
					<c:if test="${replybean.replyresult=='03'}">全部完成</c:if>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" height="40">
					反馈时间：
				</td>
				<td class="tdl">
					<bean:write name="replybean" property="replytime" />
				</td>
				<td class="tdr">
					是否超时：
				</td>
				<td class="tdl">
					<c:if test="${replybean.replyTimeSign=='-'}">
						<font color="#FF0000">超时${replybean.replyTimeLength }</font>
					</c:if>
					<c:if test="${replybean.replyTimeSign=='+'}">
						<font color="#339999">提前${replybean.replyTimeLength }</font>
					</c:if>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" height="40">
					反馈备注：
				</td>
				<td class="tdl" colspan="3">
					<bean:write name="replybean" property="replyremark" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" height="40">
					反馈附件：
				</td>
				<td class="tdl" colspan="3">
					<apptag:upload cssClass="" entityId="${replybean.id}"
						entityType="LP_SENDTASKREPLY" state="look" />
				</td>
			</tr>
			<tr class=trcolor>
				<td id="checkTaskDiv" class="tdl" colspan="4" height="40"
					style='padding-left: 10px; padding-right: 10px; padding-top: 10px; padding-bottom: 10px'>
				</td>
			</tr>
		</table>
		<apptag:appraiseDailyDaily businessId="${replybean.id}" contractorId="${contractorId}" businessModule="sendtask" displayType="view" tableClass="tabout" tableStyle="width:90%;border-top:0px;"></apptag:appraiseDailyDaily>
		<apptag:appraiseDailySpecial businessId="${replybean.id}" contractorId="${contractorId}" businessModule="sendtask" displayType="view" tableClass="tabout" tableStyle="width:90%;border-top:0px;"></apptag:appraiseDailySpecial>
		<p align="center">
			<input type="button" onclick="javascript: closeWin();" class="button"
				value="关闭" />
		</p>
		<script type="text/javascript">
		listCheckTask('${replybean.id}');
		</script>
	</body>
</html>
